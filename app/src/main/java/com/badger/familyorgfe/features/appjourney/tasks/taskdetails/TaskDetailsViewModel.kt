package com.badger.familyorgfe.features.appjourney.tasks.taskdetails

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.data.model.FamilyTask
import com.badger.familyorgfe.data.model.LocalName
import com.badger.familyorgfe.data.repository.IUserRepository
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.ext.viewModelScope
import com.badger.familyorgfe.features.appjourney.tasks.alltasks.repository.IAllTasksRepository
import com.badger.familyorgfe.features.appjourney.tasks.taskdetails.domain.CheckSubtaskOrProductUseCase
import com.badger.familyorgfe.features.appjourney.tasks.taskdetails.domain.DeleteTaskUseCase
import com.badger.familyorgfe.features.appjourney.tasks.taskdetails.repository.ICurrentTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    private val currentTaskRepository: ICurrentTaskRepository,
    private val allTasksRepository: IAllTasksRepository,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val checkSubtaskOrProductUseCase: CheckSubtaskOrProductUseCase,
    userRepository: IUserRepository
) : BaseViewModel(), ITaskDetailsViewModel {

    private val refreshCrutch: MutableStateFlow<Long> = MutableStateFlow(0L)

    override val familyTask: StateFlow<FamilyTask?> = refreshCrutch
        .flatMapLatest { flow { emit(currentTaskRepository.getCurrentTask()) } }
        .stateIn(
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
            is ITaskDetailsViewModel.Event.OnProductChecked -> longRunning {
                familyTask.value?.let { task ->
                    val arg = CheckSubtaskOrProductUseCase.Argument(
                        taskId = task.id,
                        taskProductId = event.id
                    )
                    loadingBlock {
                        checkSubtaskOrProductUseCase(arg)
                        allTasksRepository.updateData()
                        refreshCrutch.value = System.currentTimeMillis()
                    }
                }
            }
            is ITaskDetailsViewModel.Event.OnSubtaskChecked -> longRunning {
                familyTask.value?.let { task ->
                    val arg = CheckSubtaskOrProductUseCase.Argument(
                        taskId = task.id,
                        subtaskId = event.id
                    )
                    loadingBlock {
                        checkSubtaskOrProductUseCase(arg)
                        allTasksRepository.updateData()
                        refreshCrutch.value = System.currentTimeMillis()
                    }
                }
            }
            is ITaskDetailsViewModel.Event.DeleteTask -> longRunning {
                familyTask.value?.let { task ->
                    loadingBlock {
                        deleteTaskUseCase(task.id)
                        deleted.emit(true)
                    }
                }
            }
        }
    }

    private suspend fun loadingBlock(action: suspend () -> Unit) {
        if (!loading.value) {
            loading.value = true
            action()
            loading.value = false
        }
    }
}