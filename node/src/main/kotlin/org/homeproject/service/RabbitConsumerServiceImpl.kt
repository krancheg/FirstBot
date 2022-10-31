package org.homeproject.service

import org.apache.logging.log4j.LogManager
import org.homeproject.model.ContentType
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class RabbitConsumerServiceImpl(
    private val commandService: CommandService,
    private val mediaService: MediaService
) : RabbitConsumeService {

    private var log = LogManager.getLogger(RabbitConsumerServiceImpl::class.java)

    override fun consumeTextMessageUpdate(update: Update) {
        log.debug("Consume: TEXT")
        commandService.produceTextMessage(update, ContentType.TEXT)
    }

    override fun consumePhotoUpdate(update: Update) {
        log.debug("Consume: PHOTO")
        mediaService.saveMediaFile(update, ContentType.PHOTO)
    }

    override fun consumeDocUpdate(update: Update) {
        log.debug("Consume: DOCUMENT")
        mediaService.saveMediaFile(update, ContentType.DOCUMENT)
    }

}