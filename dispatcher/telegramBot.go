package main

import (
	"bytes"
	"encoding/json"
	"errors"
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"os"
	"pack.ag/amqp"
	"strconv"
	"strings"
)

const textMessageUpdate = "text_message_update"
const docMessageUpdate = "doc_message_update"
const photoMessageUpdate = "photo_message_update"
const answerMessage = "answer_message"

// https://api.telegram.org/bot<token>/METHOD_NAME
const botApi = "https://api.telegram.org/bot"
const environmentNameToken = "TELEGRAM_TOKEN"
const rabbitUser = "userok"
const rabbitPassword = "pass@1234"
const rabbitUrl = "amqp://localhost"

func main()  {
	var botUrl = botApi + os.Getenv(environmentNameToken)
	client, session, err := connectToRabbit(rabbitUrl, rabbitUser, rabbitPassword)
	if err != nil {
		return
	}
	defer disconnectFromRabbit(client)

	go waitMessageFromRabbit(botUrl, session)

	var offset = 0
	for {
		updates, err := getUpdates(botUrl, offset)
		if err != nil {
			log.Println("Smth went wrong: ", err.Error())
		}
		fmt.Println(updates)
		for _, update := range updates {
			queue, err := distributeByQueues(update)
			if err != nil {
				err = respondBotMessage(botUrl, sendMessageBuild(update, err.Error()))
				if err != nil {
					return
				}
				offset = update.UpdateId + 1
				continue
			}
			upd, err := json.Marshal(update)
			if err != nil {
				return
			}
			err = pushToRabbit(session, queue, upd)
			if err != nil {
				return
			}
			offset = update.UpdateId + 1
		}
	}
}

func distributeByQueues(update Update) (string, error)  {
	var message = update.Message
	if strings.HasPrefix(message.Text, "/") {
		return textMessageUpdate, nil
	} else if message.Photo != nil {
		return photoMessageUpdate, nil
	} else if message.Doc.FileId != "" {
		return docMessageUpdate, nil
	} else {
		return "", errors.New("Неверная команда! Список команд: /help.  Так же можно отправть фото или документ.")
	}
}

func waitMessageFromRabbit(botUrl string, session *amqp.Session) {
	for {
		data, err := pullFromRabbit(session, answerMessage)
		if err != nil {
			log.Println("Rabbit pull error: ", err)
			return
		}
		if data != nil {
			err = respond(botUrl, data)
			if err != nil {
				log.Println("Respond error: ", err)
				return
			}
		}
	}
}

func getUpdates(botUrl string, offset int) ([]Update, error) {
	resp, err := http.Get(botUrl + "/getUpdates" + "?offset=" + strconv.Itoa(offset))
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		return nil, err
	}
	var restResponse RestResponse
	err = json.Unmarshal(body, &restResponse)
	if err != nil {
		return nil, err
	}
	return restResponse.Result, nil
}

func sendMessageBuild(update Update, text string) (BotMessage)  {
	var botMessage BotMessage
	botMessage.ChatId = update.Message.Chat.ChatId
	botMessage.Text = text
	return botMessage
}

func respondBotMessage(botUrl string, botMessage BotMessage) (error) {
	buf, err := json.Marshal(botMessage)
	if err != nil {
		return err
	}
	err = respond(botUrl, buf)
	if err != nil {
		return err
	}
	return nil
}

func respond(botUrl string, buf []byte) (error) {
	_, err := http.Post(botUrl+"/sendMessage","application/json", bytes.NewBuffer(buf))
	if err != nil {
		return err
	}
	return nil
}