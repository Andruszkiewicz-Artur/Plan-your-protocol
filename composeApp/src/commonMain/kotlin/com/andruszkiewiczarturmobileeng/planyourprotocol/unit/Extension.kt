package com.andruszkiewiczarturmobileeng.planyourprotocol.unit

import androidx.compose.ui.input.key.Key.Companion.Calendar
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun Long.convertMillisToDate(): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.UTC)

    return "${localDateTime.dayOfMonth.padStart()}.${localDateTime.monthNumber.padStart()}"
}

fun Long.convertMillisToDateWithYear(): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.UTC)

    return "${localDateTime.dayOfMonth.padStart()}.${localDateTime.monthNumber.padStart()}.${localDateTime.year}"
}

fun Long.isTodayValue(todayDateDay: Int): Boolean = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.UTC).dayOfMonth == todayDateDay

fun Int.convertToTime(): String = "${this/60}:${(this%60).padEnd()}"

fun Long.convertLongToStringDate(): String {
    val todayTime = Clock.System.now().toEpochMilliseconds()
    val todayDate =  Instant.fromEpochMilliseconds(todayTime).toLocalDateTime(TimeZone.UTC)
    val yesterdayDate =  Instant.fromEpochMilliseconds(todayTime).minus(24, DateTimeUnit.HOUR).toLocalDateTime(TimeZone.UTC)
    val tomorrowDate =  Instant.fromEpochMilliseconds(todayTime).plus(24, DateTimeUnit.HOUR).toLocalDateTime(TimeZone.UTC)
    val presentingDate = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.UTC)

    return if (todayDate.dayOfMonth == presentingDate.dayOfMonth && todayDate.monthNumber == presentingDate.monthNumber && todayDate.year == presentingDate.year) "Today"
    else if (tomorrowDate.dayOfMonth == presentingDate.dayOfMonth && tomorrowDate.monthNumber == presentingDate.monthNumber && tomorrowDate.year == presentingDate.year) "Tomorrow"
    else if (yesterdayDate.dayOfMonth == presentingDate.dayOfMonth && yesterdayDate.monthNumber == presentingDate.monthNumber && yesterdayDate.year == presentingDate.year) "Yesterday"
    else "${presentingDate.dayOfMonth.padStart()}.${presentingDate.monthNumber.padStart()}"
}

fun Long.getStartAndEndOfDay(): Pair<Long, Long> {
    val instance = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.UTC)
    val day = instance.dayOfMonth
    val month = instance.monthNumber
    val year = instance.year

    val startOfTheDay = LocalDateTime(year, month, day, 0, 0, 0, 0).toInstant(TimeZone.UTC).toEpochMilliseconds()
    val endOfTheDay = LocalDateTime(year, month, day, 23, 59, 59, 0).toInstant(TimeZone.UTC).toEpochMilliseconds()

    return Pair(startOfTheDay, endOfTheDay)
}

private fun <T> T.padStart(): String = this.toString().padStart(2, '0')
private fun <T> T.padEnd(): String = this.toString().padEnd(2, '0')