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
        private const val tokenKeyName = "auth_token_key"
        private val tokenKey = stringPreferencesKey(tokenKeyName)

        private const val userEmailKeyName = "user_email_key"
        private val userEmailKey = stringPreferencesKey(userEmailKeyName)

        private const val familyIdKeyName = "family_id_key"
        private val familyKey = stringPreferencesKey(familyIdKeyName)
    }

    override val token: Flow<String> = dataStore.data
        .map { prefs -> prefs[tokenKey].orEmpty() }

    override suspend fun setToken(token: String) {
        dataStore.edit { prefs -> prefs[tokenKey] = token }
    }

    override val userEmail: Flow<String> = dataStore.data
        .map { prefs -> prefs[userEmailKey].orEmpty() }

    override suspend fun setUserEmail(id: String) {
        dataStore.edit { prefs -> prefs[userEmailKey] = id }
    }

    override val familyId: Flow<Long?> = dataStore.data
        .map { prefs -> prefs[familyKey]?.toLong() }

    override suspend fun setFamilyId(id: Long?) {
        dataStore.edit { prefs -> prefs[familyKey] = id?.toString().orEmpty() }
    }
}