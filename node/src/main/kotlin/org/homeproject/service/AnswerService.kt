package org.homeproject.service

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
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

    fun sendAnswerPhotoToChat(update: Update, file_id: String) {
        val sendPhoto = SendPhoto()
        sendPhoto.chatId = update.message.chatId.toString()
        sendPhoto.caption = "Результат выполения команды ${update.message.text}"
        sendPhoto.photo = InputFile(file_id)
        producer.produceAnswer(sendPhoto)
    }

    fun sendAnswerDocumentToChat(update: Update, file_id: String) {
        val sendDocument = SendDocument()
        sendDocument.chatId = update.message.chatId.toString()
        sendDocument.caption = "Результат выполения команды ${update.message.text}"
        sendDocument.document = InputFile(file_id)
        producer.produceAnswer(sendDocument)
    }

}