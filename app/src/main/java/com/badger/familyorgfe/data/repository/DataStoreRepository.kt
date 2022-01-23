package com.badger.familyorgfe.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : IDataStoreRepository {

    companion object {
        private const val tokenKeyName = "auth_token"
        private val tokenKey = stringPreferencesKey(tokenKeyName)

        private const val userIdKeyName = "user_id"
        private val userIdKey = stringPreferencesKey(userIdKeyName)
    }

    override val token: Flow<String> = dataStore.data.map { prefs -> prefs[tokenKey].orEmpty() }

    override suspend fun setToken(token: String) {
        dataStore.edit { prefs -> prefs[tokenKey] = token }
    }

    override val userId: Flow<String> = dataStore.data.map { prefs -> prefs[userIdKey].orEmpty() }

    override suspend fun setUserId(id: String) {
        dataStore.edit { prefs -> prefs[userIdKey] = id }
    }
}