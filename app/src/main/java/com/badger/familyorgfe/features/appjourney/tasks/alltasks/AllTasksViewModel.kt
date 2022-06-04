package com.badger.familyorgfe.features.appjourney.tasks.alltasks

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.data.model.TaskCategory
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.ext.viewModelScope
import com.badger.familyorgfe.features.appjourney.tasks.alltasks.repository.IAllTasksRepository
import com.badger.familyorgfe.features.appjourney.tasks.taskdetails.repository.ICurrentTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AllTasksViewModel @Inject constructor(
    private val allTasksRepository: IAllTasksRepository,
    private val currentTaskRepository: ICurrentTaskRepository
) : BaseViewModel(), IAllTasksViewModel {

    override val categories = MutableStateFlow(TaskCategory.getAllCategories())
    override val currentCategory = allTasksRepository.currentCategory.stateIn(
        viewModelScope(),
        started = SharingStarted.Lazily,
        initialValue = TaskCategory.All
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
                currentTaskRepository.setFamilyTaskId(null)
                allTasksRepository.updateData()
            }
            is IAllTasksViewModel.Event.OnFamilyTaskOpened -> longRunning {
                currentTaskRepository.setFamilyTaskId(event.familyTaskId)
            }
        }
    }
}