package org.homeproject.service.command

import org.homeproject.service.AnswerService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class HelpCommand(
    private val commands: List<Command>,
    private val answerService: AnswerService
): Command {

    override fun runCommand(update: Update) {
        var helpText = "Бот файлообменник\n\n"
        for (command in commands) {
            helpText += command.commandDescription() + "\n"
        }
        answerService.sendAnswerTextToChat(update, helpText)
    }

    override fun commandName(): String {
        return "/help"
    }

    override fun commandDescription(): String {
        return "/help - список команд"
    }

}