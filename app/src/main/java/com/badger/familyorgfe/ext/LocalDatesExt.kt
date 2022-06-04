package com.badger.familyorgfe.ext

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

fun Long.toInstantAtZone(): ZonedDateTime =
    Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault())