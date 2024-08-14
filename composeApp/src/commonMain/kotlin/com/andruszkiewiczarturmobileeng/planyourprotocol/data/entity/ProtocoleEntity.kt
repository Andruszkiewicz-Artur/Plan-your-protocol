package com.andruszkiewiczarturmobileeng.planyourprotocol.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.DataInfoRealizationDate

@Entity(tableName = "Protocols")
data class ProtocolEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    var idDocument: String,
    var state: DataInfoRealizationDate,
    var time: Int?,
    var date: Long,
    var resone: String?
) {
    fun toDomain(): ProtocolModule = ProtocolModule(id, idDocument, state, time, date, resone)
}
