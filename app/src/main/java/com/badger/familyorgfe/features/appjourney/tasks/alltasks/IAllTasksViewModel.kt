package com.badger.familyorgfe.features.appjourney.tasks.alltasks

import com.badger.familyorgfe.R
import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.data.model.FamilyTask
import kotlinx.coroutines.flow.StateFlow

interface IAllTasksViewModel : IBaseViewModel<IAllTasksViewModel.Event> {

    val categories: StateFlow<List<Category>>

    val currentCategory: StateFlow<Category>

    val openTasks: StateFlow<List<FamilyTask>>

    val closedTasks: StateFlow<List<FamilyTask>>

    sealed class Event {
        data class OnCategorySelected(val category: Category) : Event()
    }

    enum class Category(val resourceId: Int) {
        ALL_CATEGORY(resourceId = R.drawable.ic_all_categories),
        ONE_SHOT(resourceId = R.drawable.ic_one_shot),
        RECURRING(resourceId = R.drawable.ic_recurring),
        ONE_TIME(resourceId = R.drawable.ic_one_time)
    }
}