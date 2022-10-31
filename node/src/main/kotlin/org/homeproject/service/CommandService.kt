package org.homeproject.service

import org.homeproject.model.ContentType
import org.homeproject.service.command.Command
import org.homeproject.utils.Constant.COMMAND_DELIMITER
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class CommandService(
    private val dbService: DbService,
    private val commands: List<Command>
) {

    fun produceTextMessage(update: Update, contentType: ContentType) {
        dbService.saveQuery(update, contentType)
        val commandWithoutParams = update.message.text.split(COMMAND_DELIMITER)[0]
        for (command in commands) {
            if (commandWithoutParams == command.commandName()) {
                command.runCommand(update)
                break
            }
        }
    }

}