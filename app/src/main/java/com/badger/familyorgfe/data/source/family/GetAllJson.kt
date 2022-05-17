package com.badger.familyorgfe.data.source.family

import com.badger.familyorgfe.data.model.Family

class GetAllJson {
    class Form(

    )

    data class Response(
        val families: List<Family>,
        val invites: List<Family>
    )
}