package com.badger.familyorgfe.features.appjourney.tasks.taskdetails

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.data.model.FamilyTask
import com.badger.familyorgfe.data.model.LocalName
import com.badger.familyorgfe.data.repository.IUserRepository
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.ext.viewModelScope
import com.badger.familyorgfe.features.appjourney.tasks.taskdetails.domain.DeleteTaskUseCase
import com.badger.familyorgfe.features.appjourney.tasks.taskdetails.repository.ICurrentTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    private val currentTaskRepository: ICurrentTaskRepository,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    userRepository: IUserRepository
) : BaseViewModel(), ITaskDetailsViewModel {

    override val familyTask: StateFlow<FamilyTask?> = flow {
        emit(currentTaskRepository.getCurrentTask())
    }.stateIn(
        viewModelScope(),
        started = SharingStarted.Lazily,
        initialValue = null
    )
    override val localNames: StateFlow<List<LocalName>> = userRepository.getAllLocalNames().stateIn(
        viewModelScope(),
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )
    override val deleted = MutableSharedFlow<Boolean>()

    private val loading = MutableStateFlow(false)

    override fun onEvent(event: ITaskDetailsViewModel.Event) {
        when (event) {
            is ITaskDetailsViewModel.Event.OpenEditing -> longRunning {
                familyTask.value?.id?.let { id ->
                    currentTaskRepository.setFamilyTaskId(id)
                }
            }
            is ITaskDetailsViewModel.Event.OnProductChecked -> {

            }
            is ITaskDetailsViewModel.Event.OnSubtaskChecked -> {

            }
            is ITaskDetailsViewModel.Event.DeleteTask -> longRunning {
                familyTask.value?.let { task ->
                    if (!loading.value) {
                        loading.value = true
                        deleteTaskUseCase(task.id)
                        deleted.emit(true)
                        loading.value = false
                    }
                }
            }
        }
    }
}