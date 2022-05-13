package com.badger.familyorgfe.data.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.badger.familyorgfe.data.model.Fridge
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.data.model.User
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime


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
            val fridgeId = "fridge"

            val user = User(
                name = "",
                email = "artem_yakovlev@email.com"
            )

            val products = List(5) { id -> productMock(id.toString()) }

            val fridge = Fridge(
                id = fridgeId,
                items = products.map(Product::id),
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )

            database.userDao().insertAll(user)
            database.fridgeDao().insertAll(fridge)
            database.productDao().insertAll(*products.toTypedArray())
        }
    }

    private fun productMock(id: String) = Product(
        id = id,
        name = "Шоколадный батончик",
        quantity = 1.0,
        measure = Product.Measure.THINGS,
        category = Product.Category.HEALTHY,
        expiryDate = LocalDateTime.now().plusDays(5),
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    )
}

@DelicateCoroutinesApi
fun getPrepopulateCallback(context: Context) = PrepopulateCallback(context)