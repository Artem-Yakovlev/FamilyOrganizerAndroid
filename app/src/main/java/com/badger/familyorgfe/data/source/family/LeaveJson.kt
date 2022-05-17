package com.badger.familyorgfe.data.source.family

class LeaveJson {
    data class Form(
        val familyId: Long
    )

    data class Response(
        val success: Boolean
    )
}