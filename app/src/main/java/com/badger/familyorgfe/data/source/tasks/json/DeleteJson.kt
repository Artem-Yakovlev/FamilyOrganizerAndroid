package com.badger.familyorgfe.data.source.tasks.json

class DeleteJson {

    data class Form(
        val familyId: Long,
        val taskId: Long
    )

    class Response
}