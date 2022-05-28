package com.badger.familyorgfe.features.appjourney.tasks.alltasks

import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.data.model.FamilyTask
import kotlinx.coroutines.flow.StateFlow

interface IAllTasksViewModel : IBaseViewModel<IAllTasksViewModel.Event> {

    val categories: StateFlow<List<FamilyTask.Category>>

    val currentCategory: StateFlow<FamilyTask.Category>

    val openTasks: StateFlow<List<FamilyTask>>

    val closedTasks: StateFlow<List<FamilyTask>>

    sealed class Event {
        object Init : Event()
        data class OnCategorySelected(val category: FamilyTask.Category) : Event()
    }


}