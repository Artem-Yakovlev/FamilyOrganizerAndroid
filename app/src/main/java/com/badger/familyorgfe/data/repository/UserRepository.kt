package com.badger.familyorgfe.data.repository

import com.badger.familyorgfe.data.model.LocalName
import com.badger.familyorgfe.data.source.AppDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    database: AppDatabase
) : IUserRepository {

    private val localNameDao = database.localNamesDao()

    override fun getAllLocalNames(): Flow<List<LocalName>> {
        return localNameDao.getAll()
    }

    override suspend fun saveLocalName(localName: LocalName) {
        localNameDao.insertAll(localName)
    }
}