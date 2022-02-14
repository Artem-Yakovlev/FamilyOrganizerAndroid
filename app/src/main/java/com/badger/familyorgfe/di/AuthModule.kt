package com.badger.familyorgfe.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@DelicateCoroutinesApi
class AuthModule {

    /**
     * NavController
     * */

    @Provides
    @Singleton
    fun provideNavController(@ApplicationContext applicationContext: Context): NavController {
        return applicationContext
    }
}