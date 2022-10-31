package org.homeproject.service

import org.homeproject.model.ContentType
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class MediaService(
    private val dbService: DbService,
    private val answerService: AnswerService
) {

    fun saveMediaFile(update: Update, contentType: ContentType) {
        val id = dbService.saveQuery(update, contentType)
        answerService.sendAnswerTextID(update, id)
    }

}