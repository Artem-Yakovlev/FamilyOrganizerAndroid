package com.badger.familyorgfe.data.source.family

import com.badger.familyorgfe.data.model.Family

class GetFamilyJson {
    data class Form(
        val familyId: Long
    )

    data class Response(
        val family: Family?
    )
}