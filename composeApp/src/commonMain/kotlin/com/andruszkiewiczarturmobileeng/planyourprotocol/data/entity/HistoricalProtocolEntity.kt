package com.andruszkiewiczarturmobileeng.planyourprotocol.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.HistoricalProtocolModel
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType

@Entity(tableName = "HistoricalProtocols")
data class HistoricalProtocolEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val idProtocol: String,
    val state: ProtocolRealizationType,
    val time: Int?,
    val date: Long?,
    val editingDate: Long,
    val reason: String?,
) {
    fun toDomain(): HistoricalProtocolModel = HistoricalProtocolModel(
        idProtocol = idProtocol,
        state = state,
        time = time,
        date = date,
        editingDate = editingDate,
        reason = reason
    )
}