package com.badger.familyorgfe.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.badger.familyorgfe.data.repository.DataStoreRepository
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.source.AppDatabase
import com.badger.familyorgfe.data.source.auth.AuthApi
import com.badger.familyorgfe.data.source.family.FamilyApi
import com.badger.familyorgfe.data.source.familyauth.FamilyAuthApi
import com.badger.familyorgfe.data.source.getPrepopulateCallback
import com.badger.familyorgfe.data.source.products.ProductsApi
import com.badger.familyorgfe.data.source.user.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


private const val DATASTORE_NAME = "family-organizer-datastore"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

@Module
@InstallIn(SingletonComponent::class)
@DelicateCoroutinesApi
class SourceModule {

    companion object {
        //        private const val BASE_URL = "https://family-organizer.com/"
        private const val BASE_URL = "http://10.0.2.2:8080/"
        private const val DATABASE_NAME = "family-organizer-db"

        private const val AUTHORIZATION = "Authorization"
        private const val BEARER_PREFIX = "Bearer"
    }

    /**
     * DataStore
     * */

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext applicationContext: Context): DataStore<Preferences> {
        return applicationContext.dataStore
    }

    /**
     * Room
     * */

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .addCallback(getPrepopulateCallback(applicationContext))
            .build()
    }

    /**
     * Retrofit
     * */

    @Provides
    @Singleton
    fun provideOkHttpClient(dataStore: DataStore<Preferences>): OkHttpClient {
        val dataStoreRepository: IDataStoreRepository = DataStoreRepository(dataStore)
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val authorizationInterceptor = Interceptor { chain ->
            runBlocking { dataStoreRepository.token.firstOrNull() }
                ?.let { token ->
                    chain.request()
                        .newBuilder()
                        .addHeader(AUTHORIZATION, "$BEARER_PREFIX $token")
                        .build()
                        .let(chain::proceed)
                } ?: chain.proceed(chain.request())
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authorizationInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductsApi =
        retrofit.create(ProductsApi::class.java)

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideFamilyAuthApi(retrofit: Retrofit): FamilyAuthApi =
        retrofit.create(FamilyAuthApi::class.java)

    @Provides
    @Singleton
    fun provideFamilyApi(retrofit: Retrofit): FamilyApi =
        retrofit.create(FamilyApi::class.java)
}