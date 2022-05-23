package com.badger.familyorgfe.data.source.family.json

import com.badger.familyorgfe.data.model.Family

class GetFamilyJson {
    data class Form(
        val familyId: Long
    )

    data class Response(
        val family: Family?
    )
}