package org.homeproject.service

import org.homeproject.utils.Constant.ANSWER_MESSAGE
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class RabbitProducerServiceImp(
    private val rabbitTemplate: RabbitTemplate
    ): RabbitProducerService {

    override fun produceAnswer(answer: Any) {
        rabbitTemplate.convertAndSend(ANSWER_MESSAGE, answer)
    }

}