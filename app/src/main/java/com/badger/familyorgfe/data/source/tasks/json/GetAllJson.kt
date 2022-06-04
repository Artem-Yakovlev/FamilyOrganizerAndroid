package com.badger.familyorgfe.data.source.tasks.json

import com.badger.familyorgfe.data.source.tasks.model.RemoteTask

class GetAllJson {

    data class Form(
        val familyId: Long
    )

    data class Response(
        val tasks: List<RemoteTask>
    )
}