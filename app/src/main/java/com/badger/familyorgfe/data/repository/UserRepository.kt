package com.badger.familyorgfe.data.repository

import com.badger.familyorgfe.data.model.User
import com.badger.familyorgfe.data.source.AppDatabase
import com.badger.familyorgfe.data.source.auth.AuthApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val authApi: AuthApi,
    database: AppDatabase
) : IUserRepository {

    private val userDao = database.userDao()

    override fun getUserById(id: String): Flow<User> {
        return userDao.getById(id)
    }
}