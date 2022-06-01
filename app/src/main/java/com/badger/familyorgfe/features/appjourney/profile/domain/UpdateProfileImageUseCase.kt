package com.badger.familyorgfe.features.appjourney.profile.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.source.user.UserApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject


class UpdateProfileImageUseCase @Inject constructor(
    private val userApi: UserApi
) : BaseUseCase<File, Unit>() {

    override suspend fun invoke(arg: File) {
        try {
            val requestBody = RequestBody.create("image/*".toMediaTypeOrNull()!!, arg)
            val body = MultipartBody.Part.createFormData("profileImage", arg.name, requestBody)

            userApi.updateProfileImage(body)
        } catch (e: Exception) {
        }
    }
}