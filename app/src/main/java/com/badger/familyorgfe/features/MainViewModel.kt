package com.badger.familyorgfe.features

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.data.repository.TestRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel(), IMainViewModel {

    override val isUserAuthorized = TestRepo.isUserAuthorized/*MutableStateFlow(value = false)*/

    override fun clearData() = Unit
}