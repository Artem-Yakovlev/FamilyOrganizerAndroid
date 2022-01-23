package com.badger.familyorgfe.data.repository

import com.badger.familyorgfe.data.model.User
import com.badger.familyorgfe.data.source.AppDatabase
import com.badger.familyorgfe.data.source.UserApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApi,
    database: AppDatabase
) : IUserRepository {

    private val userDao = database.userDao()

    override fun getUserById(id: String): Flow<User> {
        return userDao.getById(id)
    }
}