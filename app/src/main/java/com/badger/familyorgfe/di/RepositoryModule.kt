package com.badger.familyorgfe.di

import com.badger.familyorgfe.data.repository.DataStoreRepository
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.repository.IUserRepository
import com.badger.familyorgfe.data.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideUserRepository(repository: UserRepository): IUserRepository

    @Binds
    abstract fun provideDataStore(dataStore: DataStoreRepository): IDataStoreRepository
}