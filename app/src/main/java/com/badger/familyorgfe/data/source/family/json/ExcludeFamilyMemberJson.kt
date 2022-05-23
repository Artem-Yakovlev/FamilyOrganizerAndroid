package com.badger.familyorgfe.data.source.family.json

class ExcludeFamilyMemberJson {

    data class Form(
        val familyId: Long,
        val memberEmail: String
    )

    data class Response(
        val success: Boolean
    )
}