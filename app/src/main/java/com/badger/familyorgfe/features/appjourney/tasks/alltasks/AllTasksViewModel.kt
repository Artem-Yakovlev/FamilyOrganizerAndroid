package com.badger.familyorgfe.features.appjourney.tasks.alltasks

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.data.model.FamilyTask
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.ext.viewModelScope
import com.badger.familyorgfe.features.appjourney.tasks.alltasks.repository.IAllTasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AllTasksViewModel @Inject constructor(
    private val allTasksRepository: IAllTasksRepository
) : BaseViewModel(), IAllTasksViewModel {

    override val categories = MutableStateFlow(FamilyTask.Category.values().toList())
    override val currentCategory = allTasksRepository.currentCategory.stateIn(
        viewModelScope(),
        started = SharingStarted.Lazily,
        initialValue = FamilyTask.Category.ALL_CATEGORY
    )

    override val openTasks = allTasksRepository.openTasks.stateIn(
        viewModelScope(),
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )
    override val closedTasks = allTasksRepository.closedTasks.stateIn(
        viewModelScope(),
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    override fun onEvent(event: IAllTasksViewModel.Event) {
        when (event) {
            is IAllTasksViewModel.Event.OnCategorySelected -> longRunning {
                allTasksRepository.changeCategory(event.category)
            }
            is IAllTasksViewModel.Event.Init -> longRunning {
                allTasksRepository.updateData()
            }
        }
    }
}