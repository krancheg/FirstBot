package org.homeproject.service

import org.homeproject.monitoring.MonitoringService
import org.homeproject.utils.Constant.ANSWER_MESSAGE
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class RabbitProducerServiceImp(
    private val rabbitTemplate: RabbitTemplate,
    private val monitoringService: MonitoringService
    ): RabbitProducerService {

    override fun produceAnswer(answer: Any) {
        monitoringService.incrementMessage(ANSWER_MESSAGE)
        rabbitTemplate.convertAndSend(ANSWER_MESSAGE, answer)
    }

}