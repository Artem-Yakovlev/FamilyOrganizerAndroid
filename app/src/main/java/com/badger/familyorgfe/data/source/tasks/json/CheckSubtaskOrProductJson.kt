package com.badger.familyorgfe.data.source.tasks.json

class CheckSubtaskOrProductJson {

    data class Form(
        val familyId: Long,
        val taskId: Long,
        val subtaskId: Long?,
        val productId: Long?
    )

    class Response
}