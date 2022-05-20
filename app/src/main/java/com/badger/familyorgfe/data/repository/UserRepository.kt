package com.badger.familyorgfe.data.repository

import com.badger.familyorgfe.data.model.LocalName
import com.badger.familyorgfe.data.model.User
import com.badger.familyorgfe.data.source.AppDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    database: AppDatabase
) : IUserRepository {

    private val userDao = database.userDao()

    private val localNameDao = database.localNamesDao()

    override fun getUserByEmail(id: String): Flow<User> {
        return userDao.getByEmail(id)
    }

    override suspend fun saveUser(user: User) {
        userDao.insertAll(user)
    }

    override suspend fun getAllLocalNames(): Flow<List<LocalName>> {
        return localNameDao.getAll()
    }

    override suspend fun saveLocalName(localName: LocalName) {
        localNameDao.insertAll(localName)
    }
}