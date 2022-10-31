package org.homeproject.service

import org.homeproject.entity.RawEntity
import org.homeproject.model.ContentType
import org.homeproject.repository.RawRepository
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class DbService(
    private val repository: RawRepository
) {

    fun getByCode(id: Long): RawEntity {
        return repository.getById(id)
    }

    fun saveQuery(update: Update, contentType: ContentType): Long {
        val rawEntity = RawEntity()
        rawEntity.event = update
        rawEntity.contentType = contentType.nameOfType
        return repository.save(rawEntity).id
    }

}