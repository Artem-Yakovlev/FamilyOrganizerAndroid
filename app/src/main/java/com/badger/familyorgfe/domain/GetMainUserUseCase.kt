package com.badger.familyorgfe.domain

import com.badger.familyorgfe.base.FlowUseCase
import com.badger.familyorgfe.data.model.User
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class GetMainUserUseCase @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository,
    private val userRepository: IUserRepository
) : FlowUseCase<Unit, User>() {

    override fun invoke(arg: Unit): Flow<User> {
        return dataStoreRepository.userId.flatMapLatest(userRepository::getUserById)
    }
}