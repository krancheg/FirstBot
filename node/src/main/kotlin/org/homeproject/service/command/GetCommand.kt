package org.homeproject.service.command

import org.homeproject.entity.RawEntity
import org.homeproject.model.ContentType
import org.homeproject.service.AnswerService
import org.homeproject.service.DbService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.regex.Pattern

@Component
class GetCommand(
    private val dbService: DbService,
    private val answerService: AnswerService) : Command {

    override fun runCommand(update: Update) {
        val pattern = Pattern.compile("(/get_)(?<num>\\d+)")
        val matcher = pattern.matcher(update.message.text)

        if (matcher.find()) {
            val id = matcher.group("num")
            val resultUpdate: RawEntity
            try {
                resultUpdate = dbService.getByCode(id.toLong())
            } catch (e: Exception) {
                answerService.sendAnswerTextToChat(update, "Неверный код медиафайла $id")
                return
            }
            if (ContentType.DOCUMENT.nameOfType == resultUpdate.contentType) {
                resultUpdate.event?.message?.document?.fileId
                    ?.let { f -> answerService.sendAnswerDocumentToChat(update, f) }
                    ?: run { answerService.sendAnswerTextToChat(update, "Документ недоступен.") }
            } else if (ContentType.PHOTO.nameOfType == resultUpdate.contentType) {
                resultUpdate.event?.message?.photo?.get(0)?.fileId
                    ?.let { f -> answerService.sendAnswerPhotoToChat(update, f) }
                    ?: run { answerService.sendAnswerTextToChat(update, "Фото недоступно.") }
            } else {
                answerService.sendAnswerTextToChat(update, "Неверный контекст.")
            }
        } else {
            answerService.sendAnswerTextToChat(update, "Некорректная команда get ${update.message.text}. Смотрите справку /help")
        }
    }

    override fun commandName(): String {
        return "/get"
    }

    override fun commandDescription(): String {
        return "/get - получить файл из базы по номеру. Шаблон: /get_<номер>"
    }

}