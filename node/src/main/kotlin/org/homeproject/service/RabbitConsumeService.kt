package org.homeproject.service

import org.homeproject.utils.Constant
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.telegram.telegrambots.meta.api.objects.Update

interface RabbitConsumeService {

    @RabbitListener(queues = [Constant.TEXT_MESSAGE_UPDATE])
    fun consumeTextMessageUpdate(update: Update)

    @RabbitListener(queues = [Constant.PHOTO_MESSAGE_UPDATE])
    fun consumePhotoUpdate(update: Update)

    @RabbitListener(queues = [Constant.DOC_MESSAGE_UPDATE])
    fun consumeDocUpdate(update: Update)

}