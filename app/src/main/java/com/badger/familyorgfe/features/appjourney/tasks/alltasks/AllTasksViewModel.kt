package com.badger.familyorgfe.features.appjourney.tasks.alltasks

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.data.model.FamilyTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AllTasksViewModel @Inject constructor() : BaseViewModel(), IAllTasksViewModel {

    override val categories = MutableStateFlow(IAllTasksViewModel.Category.values().toList())
    override val currentCategory = MutableStateFlow(IAllTasksViewModel.Category.ALL_CATEGORY)

    override val openTasks = MutableStateFlow(
        listOf(
            FamilyTask.createMock(),
            FamilyTask.createMock(),
            FamilyTask.createMock(),
        )
    )
    override val closedTasks = MutableStateFlow(
        listOf(
            FamilyTask.createMock()
        )
    )

    override fun onEvent(event: IAllTasksViewModel.Event) {
        when (event) {
            is IAllTasksViewModel.Event.OnCategorySelected -> {
                currentCategory.value = event.category
            }
        }
    }
}