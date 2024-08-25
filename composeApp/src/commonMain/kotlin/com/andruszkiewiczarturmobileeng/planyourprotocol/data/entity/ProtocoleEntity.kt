package com.andruszkiewiczarturmobileeng.planyourprotocol.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType

@Entity(tableName = "Protocols")
data class ProtocolEntity(
    @PrimaryKey(autoGenerate = false)
    var idDocument: String,
    var state: ProtocolRealizationType,
    var time: Int?,
    var date: Long?,
    var editingDate: Long,
    var resone: String?
) {
    fun toDomain(): ProtocolModule = ProtocolModule(idDocument, state, time, date, resone)
}
