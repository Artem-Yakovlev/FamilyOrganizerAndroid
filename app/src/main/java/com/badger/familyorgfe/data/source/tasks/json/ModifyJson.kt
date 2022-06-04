package com.badger.familyorgfe.data.source.tasks.json

import com.badger.familyorgfe.data.source.tasks.model.RemoteTask

class ModifyJson {

    data class Form(
        val familyId: Long,
        val task: RemoteTask
    )

    class Response
}