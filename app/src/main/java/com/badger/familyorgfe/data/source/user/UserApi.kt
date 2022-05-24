package com.badger.familyorgfe.data.source.user

import com.badger.familyorgfe.data.source.user.json.GetProfileJson
import com.badger.familyorgfe.data.source.user.json.SetTokenJson
import com.badger.familyorgfe.data.source.user.json.UpdateProfileNameJson
import com.badger.familyorgfe.data.source.user.json.UpdateStatusJson
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    companion object {
        private const val API = "/user"
        private const val GET_PROFILE = "$API/getProfile"
        private const val UPDATE_PROFILE_NAME = "$API/updateProfileName"
        private const val UPDATE_STATUS = "$API/updateStatus"
        private const val SET_TOKEN = "$API/setToken"
    }

    @POST(GET_PROFILE)
    suspend fun getProfile(@Body form: GetProfileJson.Form): GetProfileJson.Response

    @POST(UPDATE_PROFILE_NAME)
    suspend fun updateProfileName(@Body form: UpdateProfileNameJson.Form): UpdateProfileNameJson.Response

    @POST(UPDATE_STATUS)
    suspend fun updateStatus(@Body form: UpdateStatusJson.Form): UpdateStatusJson.Response

    @POST(SET_TOKEN)
    suspend fun setToken(@Body form: SetTokenJson.Form): SetTokenJson.Response
}