package com.badger.familyorgfe.data.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi


@EntryPoint
@InstallIn(SingletonComponent::class)
interface RoomCallbackEntryPoint {

    fun getDatabase(): AppDatabase

    fun getDataStore(): DataStore<Preferences>
}

@DelicateCoroutinesApi
class PrepopulateCallback(applicationContext: Context) : RoomDatabase.Callback() {

    private val entryPoint = EntryPoints.get(applicationContext, RoomCallbackEntryPoint::class.java)

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        val database = entryPoint.getDatabase()
    }
}

@DelicateCoroutinesApi
fun getPrepopulateCallback(context: Context) = PrepopulateCallback(context)