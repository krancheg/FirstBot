package org.homeproject.configuration

import org.homeproject.utils.Constant
import org.springframework.amqp.core.Queue
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class RabbitConfiguration {

    @Bean
    open fun jsonMessageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

    @Bean
    open fun textQueue(): Queue {
        return Queue(Constant.TEXT_MESSAGE_UPDATE, false)
    }

    @Bean
    open fun photoQueue(): Queue {
        return Queue(Constant.PHOTO_MESSAGE_UPDATE, false)
    }

    @Bean
    open fun docQueue(): Queue {
        return Queue(Constant.DOC_MESSAGE_UPDATE, false)
    }

    @Bean
    open fun answerQueue(): Queue {
        return Queue(Constant.ANSWER_MESSAGE, false)
    }

}