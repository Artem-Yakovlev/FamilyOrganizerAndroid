package com.badger.familyorgfe.data.source.user

import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    companion object {
        private const val API = "/user"
        private const val GET_PROFILE = "$API/getProfile"
        private const val UPDATE_PROFILE_NAME = "$API/updateProfileName"
        private const val UPDATE_STATUS = "$API/updateStatus"
    }

    @POST(GET_PROFILE)
    suspend fun getProfile(@Body form: GetProfileJson.Form): GetProfileJson.Response

    @POST(UPDATE_PROFILE_NAME)
    suspend fun updateProfileName(@Body form: UpdateProfileNameJson.Form): UpdateProfileNameJson.Response

    @POST(UPDATE_STATUS)
    suspend fun updateStatus(@Body form: UpdateStatusJson.Form): UpdateStatusJson.Response
}