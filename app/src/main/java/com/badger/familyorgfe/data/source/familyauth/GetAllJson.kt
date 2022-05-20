package com.badger.familyorgfe.data.source.familyauth

import com.badger.familyorgfe.data.model.Family

class GetAllJson {
    class Form(

    )

    data class Response(
        val families: List<Family>,
        val invites: List<Family>
    )
}