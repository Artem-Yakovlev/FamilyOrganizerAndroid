package com.badger.familyorgfe.data.source.familyauth

import com.badger.familyorgfe.data.model.Family

class CreateJson {
    data class Form(
        val familyName: String
    )

    data class Response(
        val createdId: Long,
        val families: List<Family>,
        val invites: List<Family>
    )
}