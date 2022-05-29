package com.badger.familyorgfe.features.appjourney.tasks.alltasks

import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.data.model.FamilyTask
import com.badger.familyorgfe.data.model.TaskCategory
import kotlinx.coroutines.flow.StateFlow

interface IAllTasksViewModel : IBaseViewModel<IAllTasksViewModel.Event> {

    val categories: StateFlow<List<TaskCategory>>

    val currentCategory: StateFlow<TaskCategory>

    val openTasks: StateFlow<List<FamilyTask>>

    val closedTasks: StateFlow<List<FamilyTask>>

    sealed class Event {
        object Init : Event()
        data class OnCategorySelected(val category: TaskCategory) : Event()
        data class OnFamilyTaskOpened(val familyTaskId: Long) : Event()
    }


}