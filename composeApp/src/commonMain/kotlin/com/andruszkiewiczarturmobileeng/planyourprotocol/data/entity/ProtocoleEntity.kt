package com.andruszkiewiczarturmobileeng.planyourprotocol.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType

@Entity(tableName = "Protocols")
data class ProtocolEntity(
    @PrimaryKey(autoGenerate = false)
    val idDocument: String,
    val state: ProtocolRealizationType,
    val time: Int?,
    val date: Long?,
    val editingDate: Long,
    val resone: String?,
) {
    fun toDomain(): ProtocolModule = ProtocolModule(idDocument, state, time, date, resone)
}
