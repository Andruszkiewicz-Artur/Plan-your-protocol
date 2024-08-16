package com.andruszkiewiczarturmobileeng.planyourprotocol.unit

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.convertMillisToDate(): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.UTC)

    val dayOfMonth = localDateTime.dayOfMonth
    val month = localDateTime.monthNumber

    return "$dayOfMonth." + month.toString().padStart(2, '0')
}