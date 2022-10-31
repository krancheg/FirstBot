package org.homeproject.service

import org.homeproject.utils.Constant.ANSWER_MESSAGE
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Service
class RabbitProducerServiceImp(
    private val rabbitTemplate: RabbitTemplate
    ): RabbitProducerService {

    override fun produceAnswer(sendMessage: SendMessage) {
        rabbitTemplate.convertAndSend(ANSWER_MESSAGE, sendMessage)
    }

}