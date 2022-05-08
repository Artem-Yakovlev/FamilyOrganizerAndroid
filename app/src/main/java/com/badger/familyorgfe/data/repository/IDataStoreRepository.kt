package com.badger.familyorgfe.data.repository

import kotlinx.coroutines.flow.Flow

interface IDataStoreRepository {

    val token: Flow<String>

    suspend fun setToken(token: String)

    val userId: Flow<String>

    suspend fun setUserEmail(id: String)
}