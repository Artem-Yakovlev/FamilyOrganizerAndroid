package com.badger.familyorgfe.data.repository

import com.badger.familyorgfe.data.model.LocalName
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    fun getAllLocalNames(): Flow<List<LocalName>>

    suspend fun saveLocalName(localName: LocalName)
}