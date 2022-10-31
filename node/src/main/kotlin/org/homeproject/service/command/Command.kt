package org.homeproject.service.command

import org.telegram.telegrambots.meta.api.objects.Update

interface Command {

    fun runCommand(update: Update)

    fun commandName(): String
    fun commandDescription(): String

}