package org.homeproject.service


interface RabbitProducerService {
    fun produceAnswer(answer: Any)
}