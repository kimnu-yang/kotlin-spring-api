package io.directional.recruitment.db.index

import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface IndexRepository: JpaRepository<IndexEntity, Long> {

    // SELET * FROM index WHERE code = :code
    fun findByCode(code: String): Optional<IndexEntity>

    // SELECT * FROM index WHERE name LIKE %:name%
    fun findByNameContaining(name: String, sort: Sort): List<IndexEntity>
}