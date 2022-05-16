package com.badger.familyorgfe.data.repository

import kotlinx.coroutines.flow.Flow

interface IDataStoreRepository {

    val token: Flow<String>

    suspend fun setToken(token: String)

    val userEmail: Flow<String>

    suspend fun setUserEmail(id: String)

    val familyId: Flow<Long?>

    suspend fun setFamilyId(id: Long?)
}