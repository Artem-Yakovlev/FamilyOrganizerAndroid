package com.badger.familyorgfe.data.source.family

import com.badger.familyorgfe.data.model.Family

class AcceptInviteJson {
    data class Form(
        val familyId: Long
    )

    data class Response(
        val success: Boolean,
        val family: Family?
    )
}