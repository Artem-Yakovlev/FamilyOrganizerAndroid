package com.badger.familyorgfe.data.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.data.model.User
import com.badger.familyorgfe.data.model.UserStatus
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


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

        GlobalScope.launch(Dispatchers.IO) {
            val user = User(
                name = "",
                email = "artem_yakovlev@email.com",
                imageUrl = "",
                status = UserStatus.UNDEFINED
            )

            val products = List(5) { id -> productMock(id.toLong()) }

            database.userDao().insertAll(user)
            database.productDao().insertAll(*products.toTypedArray())
        }
    }

    private fun productMock(id: Long) = Product(
        id = id,
        name = "Шоколадный батончик",
        quantity = 1.0,
        measure = Product.Measure.THINGS,
        category = Product.Category.HEALTHY,
        expiryMillis = null
    )
}

@DelicateCoroutinesApi
fun getPrepopulateCallback(context: Context) = PrepopulateCallback(context)