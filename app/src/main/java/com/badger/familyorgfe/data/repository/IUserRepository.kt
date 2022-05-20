package com.badger.familyorgfe.data.repository

import com.badger.familyorgfe.data.model.LocalName
import com.badger.familyorgfe.data.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    suspend fun saveUser(user: User)

    suspend fun getAllLocalNames(): Flow<List<LocalName>>

    suspend fun saveLocalName(localName: LocalName)
}