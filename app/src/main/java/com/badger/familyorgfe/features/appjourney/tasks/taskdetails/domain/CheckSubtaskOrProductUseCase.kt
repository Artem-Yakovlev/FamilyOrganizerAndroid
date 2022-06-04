package com.badger.familyorgfe.features.appjourney.tasks.taskdetails.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.source.tasks.TasksApi
import com.badger.familyorgfe.data.source.tasks.json.CheckSubtaskOrProductJson
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class CheckSubtaskOrProductUseCase @Inject constructor(
    val dataStoreRepository: IDataStoreRepository,
    val tasksApi: TasksApi
) : BaseUseCase<CheckSubtaskOrProductUseCase.Argument, Unit>() {

    class Argument(
        val taskId: Long,
        val subtaskId: Long? = null,
        val taskProductId: Long? = null
    )

    override suspend fun invoke(arg: Argument) {
        dataStoreRepository.familyId.firstOrNull()?.let { familyId ->
            val form = CheckSubtaskOrProductJson.Form(
                familyId = familyId,
                taskId = arg.taskId,
                subtaskId = arg.subtaskId,
                productId = arg.taskProductId
            )

            try {
                tasksApi.checkSubtaskOrProduct(form)
            } catch (e: Exception) {

            }
        }
    }


}