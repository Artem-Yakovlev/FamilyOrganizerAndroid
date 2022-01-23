package com.badger.familyorgfe.data.repository

import com.badger.familyorgfe.data.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    fun getUserById(id: String): Flow<User>
}