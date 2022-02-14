package com.badger.familyorgfe.data.repository

import kotlinx.coroutines.flow.Flow

interface IDataStoreRepository {

    val token: Flow<String>

    suspend fun setToken(token: String)

    val userId: Flow<String>

    suspend fun setUserId(id: String)

    val isUserAuthorized: Flow<Boolean>

    suspend fun setIsUserAuthorized(isUserAuthorized: Boolean)
}