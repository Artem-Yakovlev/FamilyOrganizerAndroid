package com.badger.familyorgfe.features.authjourney.code.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.source.auth.AuthApi
import com.badger.familyorgfe.data.source.auth.CheckCodeJson
import kotlinx.coroutines.delay
import javax.inject.Inject

class VerifyCodeUseCase @Inject constructor(
    private val api: AuthApi,
    private val dataStoreRepository: IDataStoreRepository
) : BaseUseCase<CheckCodeJson.Form, Boolean>() {

    override suspend fun invoke(arg: CheckCodeJson.Form): Boolean {
//        val response = api.checkCode(arg)
//        response.token?.let { dataStoreRepository.setToken(it) }

        delay(500)
//        return response.success
        return true
    }
}