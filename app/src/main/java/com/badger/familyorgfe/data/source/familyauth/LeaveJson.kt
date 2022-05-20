package com.badger.familyorgfe.data.source.familyauth

class LeaveJson {
    data class Form(
        val familyId: Long
    )

    data class Response(
        val success: Boolean
    )
}