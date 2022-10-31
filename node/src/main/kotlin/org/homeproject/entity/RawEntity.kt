package org.homeproject.entity

import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.telegram.telegrambots.meta.api.objects.Update
import javax.persistence.*

@Entity
@Table(name ="raw_data")
@TypeDef(
    name = "jsonb",
    typeClass = JsonBinaryType::class
)
open class RawEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    var event: Update? = null

    var contentType: String? = null

}