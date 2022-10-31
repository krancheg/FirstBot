package org.homeproject.service

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class AnswerService(
    private val producer: RabbitProducerService
) {

    fun sendAnswerTextID(update: Update, id: Long) {
        sendAnswerTextToChat(update, "Файл сохранен. Для доступа введите: /get_$id")
    }

    fun sendAnswerTextToChat(update: Update, text: String) {
        val sendMessage = SendMessage()
        sendMessage.chatId = update.message.chatId.toString()
        sendMessage.text = text
        producer.produceAnswer(sendMessage)
    }

}