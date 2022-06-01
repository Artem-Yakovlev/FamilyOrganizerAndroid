package com.badger.familyorgfe.ext

import android.util.Patterns
import org.threeten.bp.DateTimeException
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

fun String.isValidMail() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidUserName() = trim().isNotEmpty() && trim().length >= 2

fun String.isValidProductName() = trim().length >= 2

const val MAX_TASK_TITLE_LENGTH = 30
fun String.isValidTaskTitle() = trim().length >= 3
fun String.isValidSubtaskTitle() = trim().length >= 3

private const val DATE_DOT = "."
private const val N_DATE_SEGMENTS = 3
fun String.convertToLocalDate(): LocalDate? {
    return split(DATE_DOT)
        .mapNotNull(String::toIntOrNull)
        .takeIf { it.size == N_DATE_SEGMENTS }
        ?.let { segments ->
            try {
                LocalDate.of(segments[2], segments[1], segments[0])
            } catch (e: DateTimeException) {
                null
            }
        }

}

fun String.convertToFutureLocalDate(): LocalDate? {
    return convertToLocalDate()?.takeIf { date ->
        val current = LocalDate.now()
        date.isAfter(current) || current == date
    }
}

private const val MAX_YEARS_DIFFERENCE = 50
fun String.convertToRealFutureDate() = convertToFutureLocalDate()
    ?.takeIf { date -> date.year - LocalDate.now().year <= MAX_YEARS_DIFFERENCE }


private const val TIME_COLON = ":"
private const val N_TIME_SEGMENTS = 2
private const val FIRST_TIME_SEGMENT_LIMITATION = 23
private const val SECOND_TIME_SEGMENTS_LIMITATION = 59

fun String.filterTimeSymbols() = filter { it == TIME_COLON.first() || it.isDigit() }
fun String.filterDateSymbols() = filter { it == DATE_DOT.first() || it.isDigit() }

fun String.convertToLocalTime(): LocalTime? {
    return split(TIME_COLON)
        .mapNotNull(String::toIntOrNull)
        .takeIf {
            it.size == N_TIME_SEGMENTS
                    && it.first() <= FIRST_TIME_SEGMENT_LIMITATION && it.first() >= 0
                    && it.last() <= SECOND_TIME_SEGMENTS_LIMITATION && it.last() >= 0
        }?.let { segments ->
            try {
                LocalTime.of(segments.first(), segments.last())
            } catch (e: DateTimeException) {
                null
            }
        }

}

fun String.convertToRealFutureTime() = convertToLocalTime()