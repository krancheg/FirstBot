package main

// Rabbitmq enabled plugin: rabbitmq-plugins enable rabbitmq_amqp1_0

import (
	"context"
	"log"
	"time"

	"pack.ag/amqp" // Install command:  go get -u -v pack.ag/amqp
)

func connectToRabbit(rabbitUrl, user, password string) (*amqp.Client, *amqp.Session, error) {
	// Create client
	client, err := amqp.Dial(rabbitUrl,
		amqp.ConnSASLPlain(user, password),
	)
	if err != nil {
		log.Fatal("Dialing AMQP server:", err)
		return nil, nil, err
	}

	// Open a session
	session, err := client.NewSession()
	if err != nil {
		log.Fatal("Creating AMQP session:", err)
		client.Close()
		return nil, nil, err
	}

	return client, session, nil
}

func disconnectFromRabbit(client *amqp.Client) {
	err := client.Close()
	if err != nil {
		log.Fatal("Don't disconnect from AMQP:", err)
	}
}

func pushToRabbit(session *amqp.Session, queue string, message []byte) (error) {

	ctx := context.Background()

	// Create a sender
	sender, err := session.NewSender(
		amqp.LinkTargetAddress(queue),
		)
	if err != nil {
		log.Fatal("Creating sender link:", err)
		return err
	}

	ctx, cancel := context.WithTimeout(ctx, 5*time.Second)

	// Send message
	err = sender.Send(ctx, amqp.NewMessage(message))
	if err != nil {
		log.Fatal("Sending message:", err)
		return err
	}

	sender.Close(ctx)
	cancel()
	return nil
}

func pullFromRabbit(session *amqp.Session, queue string) ([]byte, error) {

	ctx := context.Background()

	receiver, err := session.NewReceiver(
		amqp.LinkSourceAddress(queue),
		amqp.LinkCredit(10),
		)
	if err != nil {
		log.Fatal("Creating receiver link:", err)
		return nil, err
	}
	defer func() {
		ctx, cancel := context.WithTimeout(ctx, 1*time.Second)
		receiver.Close(ctx)
		cancel()
	}()

	for {
		// Receive next message
		msg, err := receiver.Receive(ctx)
		if err != nil {
			log.Fatal("Reading message from AMQP:", err)
			return nil, err
		}

		// Accept message
		msg.Accept()

		return msg.GetData(), nil
	}
}

