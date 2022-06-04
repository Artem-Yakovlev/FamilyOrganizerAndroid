package com.badger.familyorgfe.data.source.tasks

import com.badger.familyorgfe.base.BaseResponse
import com.badger.familyorgfe.data.source.tasks.json.*
import retrofit2.http.Body
import retrofit2.http.POST

interface TasksApi {
    companion object {
        private const val API = "/tasks"
        private const val GET_ALL = "$API/getAll"
        private const val CREATE = "$API/create"
        private const val DELETE = "$API/delete"
        private const val MODIFY = "$API/modify"
        private const val CHANGE_STATUS = "$API/changeStatus"
    }

    @POST(GET_ALL)
    suspend fun getAll(@Body form: GetAllJson.Form): BaseResponse<GetAllJson.Response>

    @POST(CREATE)
    suspend fun create(@Body form: CreateJson.Form): BaseResponse<CreateJson.Response>

    @POST(DELETE)
    suspend fun delete(@Body form: DeleteJson.Form): BaseResponse<DeleteJson.Response>

    @POST(MODIFY)
    suspend fun modify(@Body form: ModifyJson.Form): BaseResponse<ModifyJson.Response>

    @POST(CHANGE_STATUS)
    suspend fun changeStatus(@Body form: ChangeStatusJson.Form): BaseResponse<ChangeStatusJson.Response>
}