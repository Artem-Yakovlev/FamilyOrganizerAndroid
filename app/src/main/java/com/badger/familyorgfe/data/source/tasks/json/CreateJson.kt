package com.badger.familyorgfe.data.source.tasks.json

import com.badger.familyorgfe.data.source.tasks.model.RemoteTask

class CreateJson {

    data class Form(
        val familyId: Long,
        val task: RemoteTask
    )

    class Response
}