package org.homeproject.service

import io.micrometer.core.annotation.Timed
import org.homeproject.entity.RawEntity
import org.homeproject.model.ContentType
import org.homeproject.repository.RawRepository
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class DbService(
    private val repository: RawRepository
) {

    @Retryable(maxAttempts = 3, backoff = Backoff(multiplier = 1.3, maxDelay = 5000))
    @Timed("get_from_code")
    fun getByCode(id: Long): RawEntity {
        return repository.getById(id)
    }

    @Retryable(maxAttempts = 3, backoff = Backoff(multiplier = 1.3, maxDelay = 5000))

    @Timed("save_to_base")
    fun saveQuery(update: Update, contentType: ContentType): Long {
        val rawEntity = RawEntity()
        rawEntity.event = update
        rawEntity.contentType = contentType.nameOfType
        return repository.save(rawEntity).id
    }

}