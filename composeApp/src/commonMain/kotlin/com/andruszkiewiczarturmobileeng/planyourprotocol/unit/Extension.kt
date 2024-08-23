package com.andruszkiewiczarturmobileeng.planyourprotocol.unit

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.convertMillisToDate(): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.UTC)

    return "${localDateTime.dayOfMonth}.${localDateTime.monthNumber.padStart()}"
}

fun Int.convertToTime(): String = "${this/60}:${(this%60).padEnd()}"

private fun <T> T.padStart(): String = this.toString().padStart(2, '0')
private fun <T> T.padEnd(): String = this.toString().padEnd(2, '0')