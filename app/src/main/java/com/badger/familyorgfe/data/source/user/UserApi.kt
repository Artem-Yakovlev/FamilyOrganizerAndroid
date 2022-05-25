package com.badger.familyorgfe.data.source.user

import com.badger.familyorgfe.data.source.user.json.*
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UserApi {

    companion object {
        private const val API = "/user"
        private const val GET_PROFILE = "$API/getProfile"
        private const val UPDATE_PROFILE_NAME = "$API/updateProfileName"
        private const val UPDATE_STATUS = "$API/updateStatus"
        private const val SET_TOKEN = "$API/setToken"
        private const val UPDATE_PROFILE_IMAGE = "$API/updateProfileImage"
    }

    @POST(GET_PROFILE)
    suspend fun getProfile(@Body form: GetProfileJson.Form): GetProfileJson.Response

    @POST(UPDATE_PROFILE_NAME)
    suspend fun updateProfileName(@Body form: UpdateProfileNameJson.Form): UpdateProfileNameJson.Response

    @POST(UPDATE_STATUS)
    suspend fun updateStatus(@Body form: UpdateStatusJson.Form): UpdateStatusJson.Response

    @POST(SET_TOKEN)
    suspend fun setToken(@Body form: SetTokenJson.Form): SetTokenJson.Response

    @Multipart
    @POST(UPDATE_PROFILE_IMAGE)
    suspend fun updateProfileImage(@Part profileImage: MultipartBody.Part): UpdateProfileImageJson.Response
}