package com.badger.familyorgfe.di

import com.badger.familyorgfe.navigation.NavigationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@DelicateCoroutinesApi
class AuthModule {

    /**
     * NavigationManager
     * */

    @Provides
    @Singleton
    fun providesNavigationManager() = NavigationManager()

}