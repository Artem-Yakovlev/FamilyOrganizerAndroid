package com.badger.familyorgfe.data.repository

import com.badger.familyorgfe.data.source.AppDatabase
import com.badger.familyorgfe.data.source.UserApi
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApi,
    database: AppDatabase
) : IUserRepository {

    private val userDao = database.userDao()
}