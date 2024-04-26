package io.directional.recruitment.db.index

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "index")
data class IndexEntity (
    @Id
    @Column(length = 5, nullable = false)
    var code: String?= null,

    @Column(length = 100, nullable = false)
    var name: String?= null,

    @Column(nullable = false)
    var changeRate: Double?= null,

    @Column(nullable = false)
    var updatedAt: LocalDateTime?= null
)