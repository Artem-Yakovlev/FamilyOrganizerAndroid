package com.badger.familyorgfe.data.repository

import com.badger.familyorgfe.data.model.LocalName
import com.badger.familyorgfe.data.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    fun getUserByEmail(id: String): Flow<User>

    suspend fun saveUser(user: User)

    suspend fun getAllLocalNames(): List<LocalName>
}