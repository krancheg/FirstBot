package org.homeproject.service

import org.telegram.telegrambots.meta.api.methods.send.SendMessage

interface RabbitProducerService {
    fun produceAnswer(sendMessage: SendMessage)
}