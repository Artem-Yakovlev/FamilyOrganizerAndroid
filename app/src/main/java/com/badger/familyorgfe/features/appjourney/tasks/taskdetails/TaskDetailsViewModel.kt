package com.badger.familyorgfe.features.appjourney.tasks.taskdetails

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.data.model.FamilyTask
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.ext.viewModelScope
import com.badger.familyorgfe.features.appjourney.tasks.taskdetails.repository.ICurrentTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    private val currentTaskRepository: ICurrentTaskRepository
) : BaseViewModel(), ITaskDetailsViewModel {

    override val familyTask: StateFlow<FamilyTask?> = currentTaskRepository.currentTask.stateIn(
        viewModelScope(),
        started = SharingStarted.Lazily,
        initialValue = null
    )

    override fun onEvent(event: ITaskDetailsViewModel.Event) {
        when (event) {
            ITaskDetailsViewModel.Event.OpenEditing -> {

            }
        }
    }

    override fun onCleared() = longRunning {
        currentTaskRepository.setFamilyTaskId(null)
    }
}