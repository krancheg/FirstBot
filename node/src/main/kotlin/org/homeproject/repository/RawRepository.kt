package org.homeproject.repository

import org.homeproject.entity.RawEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RawRepository : JpaRepository<RawEntity, Long>{
}