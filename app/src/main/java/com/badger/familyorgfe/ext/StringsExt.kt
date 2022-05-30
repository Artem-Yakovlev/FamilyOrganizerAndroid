package com.badger.familyorgfe.ext

import android.util.Patterns
import org.threeten.bp.DateTimeException
import org.threeten.bp.LocalDate

fun String.isValidMail() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidUserName() = trim().isNotEmpty() && trim().length >= 2

fun String.isValidProductName() = trim().length >= 2

const val MAX_TASK_TITLE_LENGTH = 30
fun String.isValidTaskTitle() = trim().length >= 3

private const val DATE_DOT = "."
private const val N_SEGMENTS = 3
fun String.convertToLocalDate(): LocalDate? {
    return split(DATE_DOT)
        .mapNotNull(String::toIntOrNull)
        .takeIf { it.size == N_SEGMENTS }
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