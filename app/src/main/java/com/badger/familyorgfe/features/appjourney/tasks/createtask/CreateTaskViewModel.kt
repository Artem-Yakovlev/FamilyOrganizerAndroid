package com.badger.familyorgfe.features.appjourney.tasks.createtask

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.data.model.Subtask
import com.badger.familyorgfe.data.model.TaskCategory
import com.badger.familyorgfe.data.model.TaskProduct
import com.badger.familyorgfe.ext.*
import com.badger.familyorgfe.features.appjourney.tasks.createtask.domain.CreateNotificationsDialogStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val createNotificationsDialogStateUseCase: CreateNotificationsDialogStateUseCase
) : BaseViewModel(), ICreateTaskViewModel {

    override val state =
        MutableStateFlow(ICreateTaskViewModel.State.createEmpty())
    override val categoriesDialogState =
        MutableStateFlow<ICreateTaskViewModel.CategoriesDialogState?>(null)
    override val notificationsDialogState =
        MutableStateFlow<ICreateTaskViewModel.NotificationDialogState?>(null)
    override val subtasksDialogState =
        MutableStateFlow<ICreateTaskViewModel.SubtasksDialogState?>(null)
    override val productsDialogState =
        MutableStateFlow<ICreateTaskViewModel.ProductDialogState?>(null)

    override fun onEvent(event: ICreateTaskViewModel.Event) {
        when (event) {
            is ICreateTaskViewModel.Event.Ordinal -> onOrdinalEvent(event)
            is ICreateTaskViewModel.Event.Categories -> onCategoriesEvent(event)
            is ICreateTaskViewModel.Event.Notifications -> onNotificationsEvent(event)
            is ICreateTaskViewModel.Event.Subtasks -> onSubtasksEvent(event)
            is ICreateTaskViewModel.Event.Products -> onProductsEvent(event)
        }
    }

    private fun onOrdinalEvent(event: ICreateTaskViewModel.Event.Ordinal) {
        when (event) {
            is ICreateTaskViewModel.Event.Ordinal.Init -> {

            }
            is ICreateTaskViewModel.Event.Ordinal.OnDoneClicked -> {

            }
            is ICreateTaskViewModel.Event.Ordinal.OnDescriptionValueChanged -> {
                state.value = state.value.copy(description = event.value)
            }
            is ICreateTaskViewModel.Event.Ordinal.OnTitleValueChanged -> {
                state.value = state.value.copy(title = event.value.take(MAX_TASK_TITLE_LENGTH))
            }
            is ICreateTaskViewModel.Event.Ordinal.OpenNotifications -> longRunning {
                notificationsDialogState.value = createNotificationsDialogStateUseCase(
                    arg = event.notifications
                )
            }
            is ICreateTaskViewModel.Event.Ordinal.OpenSubtasks -> longRunning {
                subtasksDialogState.value = ICreateTaskViewModel.SubtasksDialogState.create(
                    items = event.subtasks
                )
            }
            is ICreateTaskViewModel.Event.Ordinal.OpenProducts -> longRunning {
                productsDialogState.value = ICreateTaskViewModel.ProductDialogState.create(
                    items = event.products
                )
            }
            is ICreateTaskViewModel.Event.Ordinal.OpenCategories -> longRunning {
                categoriesDialogState.value = ICreateTaskViewModel.CategoriesDialogState.create(
                    category = event.category
                )
            }
        }
    }

    /**
     * Categories
     * */

    private fun onCategoriesEvent(event: ICreateTaskViewModel.Event.Categories) {
        when (event) {
            is ICreateTaskViewModel.Event.CreatingOneTimeCategory -> {
                onOneTimeCategoryEvent(event)
            }
            is ICreateTaskViewModel.Event.CreatingDaysOfWeekCategory -> {
                onDaysOfWeekCategoryEvent(event)
            }
            is ICreateTaskViewModel.Event.CreatingEveryYearCategory -> {
                onEveryYearCategoryEvent(event)
            }
            is ICreateTaskViewModel.Event.Categories.Dismiss -> {
                categoriesDialogState.value = null
            }
            is ICreateTaskViewModel.Event.Categories.OnDaysOfWeekCategoryClicked -> {
                categoriesDialogState.value = categoriesDialogState.value?.copy(
                    creatingState = ICreateTaskViewModel.CategoriesDialogState
                        .CreatingState.DaysOfWeekCategory
                )
            }
            is ICreateTaskViewModel.Event.Categories.OnEveryYearCategoryClicked -> {
                categoriesDialogState.value = categoriesDialogState.value?.copy(
                    creatingState = ICreateTaskViewModel.CategoriesDialogState
                        .CreatingState.EveryYearCategory.createEmpty()
                )
            }
            is ICreateTaskViewModel.Event.Categories.OnOneShotCategoryClicked -> {
                state.value = state.value.copy(category = TaskCategory.OneShot)
                categoriesDialogState.value = null
            }
            is ICreateTaskViewModel.Event.Categories.OnOneTimeCategoryClicked -> {
                categoriesDialogState.value = categoriesDialogState.value?.copy(
                    creatingState = ICreateTaskViewModel.CategoriesDialogState
                        .CreatingState.OneTimeCategory.createEmpty()
                )
            }
        }
    }

    private fun onOneTimeCategoryEvent(
        event: ICreateTaskViewModel.Event.CreatingOneTimeCategory
    ) {
        val creatingState = categoriesDialogState.value?.creatingState
                as? ICreateTaskViewModel.CategoriesDialogState.CreatingState.OneTimeCategory
            ?: return

        when (event) {
            is ICreateTaskViewModel.Event.CreatingOneTimeCategory.Dismiss -> {
                dismissCategoryCreatingDialog()
            }
            is ICreateTaskViewModel.Event.CreatingOneTimeCategory.OnDateChanged -> {
                categoriesDialogState.value = categoriesDialogState.value?.copy(
                    creatingState = creatingState.copy(
                        dateString = event.date.filterDateSymbols()
                    )
                )
            }
            is ICreateTaskViewModel.Event.CreatingOneTimeCategory.OnTimeChanged -> {
                categoriesDialogState.value = categoriesDialogState.value?.copy(
                    creatingState = creatingState.copy(
                        timeString = event.time.filterTimeSymbols()
                    )
                )
            }
            is ICreateTaskViewModel.Event.CreatingOneTimeCategory.Save -> {
                state.value = state.value.copy(
                    category = creatingState.toOneTimeCategory()
                )
                categoriesDialogState.value = null
            }
        }
    }

    private fun onDaysOfWeekCategoryEvent(
        event: ICreateTaskViewModel.Event.CreatingDaysOfWeekCategory
    ) {
        when (event) {
            ICreateTaskViewModel.Event.CreatingDaysOfWeekCategory.Dismiss -> {
                dismissCategoryCreatingDialog()
            }
        }
    }

    private fun onEveryYearCategoryEvent(
        event: ICreateTaskViewModel.Event.CreatingEveryYearCategory
    ) {
        val creatingState = categoriesDialogState.value?.creatingState
                as? ICreateTaskViewModel.CategoriesDialogState.CreatingState.EveryYearCategory
            ?: return

        when (event) {
            is ICreateTaskViewModel.Event.CreatingEveryYearCategory.Dismiss -> {
                dismissCategoryCreatingDialog()
            }
            is ICreateTaskViewModel.Event.CreatingEveryYearCategory.OnDateChanged -> {
                categoriesDialogState.value = categoriesDialogState.value?.copy(
                    creatingState = creatingState.copy(
                        dateString = event.date.filterDateSymbols()
                    )
                )
            }
            is ICreateTaskViewModel.Event.CreatingEveryYearCategory.OnTimeChanged -> {
                categoriesDialogState.value = categoriesDialogState.value?.copy(
                    creatingState = creatingState.copy(
                        timeString = event.time.filterTimeSymbols()
                    )
                )
            }
            is ICreateTaskViewModel.Event.CreatingEveryYearCategory.Save -> {
                state.value = state.value.copy(
                    category = creatingState.toEveryYearCategory()
                )
                categoriesDialogState.value = null
            }
        }
    }

    private fun dismissCategoryCreatingDialog() {
        categoriesDialogState.value = categoriesDialogState.value?.copy(
            creatingState = null
        )
    }

    /**
     * Notifications
     * */

    private fun onNotificationsEvent(event: ICreateTaskViewModel.Event.Notifications) {
        when (event) {
            is ICreateTaskViewModel.Event.Notifications.Checked -> {
                notificationsDialogState.value = notificationsDialogState.value?.copy(
                    items = notificationsDialogState.value?.items?.map { item ->
                        if (item.email == event.email) {
                            item.copy(checked = !item.checked)
                        } else {
                            item
                        }
                    }.orEmpty()
                )
            }
            is ICreateTaskViewModel.Event.Notifications.Dismiss -> {
                notificationsDialogState.value = null
            }
            is ICreateTaskViewModel.Event.Notifications.Save -> {
                state.value = state.value.copy(
                    notifications = notificationsDialogState.value?.toNotifications().orEmpty()
                )
                notificationsDialogState.value = null
            }
        }
    }

    /**
     * Subtasks
     * */

    private fun onSubtasksEvent(event: ICreateTaskViewModel.Event.Subtasks) {
        when (event) {
            is ICreateTaskViewModel.Event.CreatingSubtask -> {
                onSubtasksCreatingEvent(event)
            }
            is ICreateTaskViewModel.Event.Subtasks.Delete -> {
                subtasksDialogState.value = subtasksDialogState.value?.copy(
                    items = subtasksDialogState.value?.items
                        ?.filter { subtask -> subtask.text != event.title }
                        .orEmpty()
                )
            }
            is ICreateTaskViewModel.Event.Subtasks.Dismiss -> {
                subtasksDialogState.value = null
            }
            is ICreateTaskViewModel.Event.Subtasks.Save -> {
                state.value = state.value.copy(
                    subtasks = subtasksDialogState.value?.items.orEmpty()
                )
                subtasksDialogState.value = null
            }
            is ICreateTaskViewModel.Event.Subtasks.Create -> {
                subtasksDialogState.value = subtasksDialogState.value?.copy(
                    creatingState = ICreateTaskViewModel.SubtasksDialogState
                        .CreatingState.createEmpty()
                )
            }
        }
    }

    private fun onSubtasksCreatingEvent(event: ICreateTaskViewModel.Event.CreatingSubtask) {
        when (event) {
            is ICreateTaskViewModel.Event.CreatingSubtask.Dismiss -> {
                subtasksDialogState.value = subtasksDialogState.value?.copy(
                    creatingState = null
                )
            }
            is ICreateTaskViewModel.Event.CreatingSubtask.OnTitleChanged -> {
                subtasksDialogState.value = subtasksDialogState.value?.copy(
                    creatingState = subtasksDialogState.value?.creatingState?.copy(
                        title = event.title,
                        enabled = event.title.isValidSubtaskTitle()
                                && subtasksDialogState.value
                            ?.items?.all { it.text != event.title } == true
                    )
                )
            }
            is ICreateTaskViewModel.Event.CreatingSubtask.Save -> {
                subtasksDialogState.value?.creatingState?.toSubtask()?.let { subtask ->
                    val actualItems = subtasksDialogState.value?.items.orEmpty() + subtask

                    subtasksDialogState.value = subtasksDialogState.value?.copy(
                        items = actualItems.distinctBy(Subtask::text).sortedBy(Subtask::text),
                        creatingState = null
                    )
                }
            }
        }
    }

    /**
     * Products
     * */

    private fun onProductsEvent(event: ICreateTaskViewModel.Event.Products) {
        when (event) {
            is ICreateTaskViewModel.Event.CreatingProducts -> onProductsCreatingEvent(event)
            is ICreateTaskViewModel.Event.Products.Create -> {
                productsDialogState.value = productsDialogState.value?.copy(
                    creatingState = ICreateTaskViewModel.ProductDialogState
                        .CreatingState.createEmpty()
                )
            }
            is ICreateTaskViewModel.Event.Products.Delete -> {
                productsDialogState.value = productsDialogState.value?.copy(
                    items = productsDialogState.value?.items
                        ?.filter { subtask -> subtask.title != event.title }
                        .orEmpty()
                )
            }
            is ICreateTaskViewModel.Event.Products.Dismiss -> {
                productsDialogState.value = null
            }
            is ICreateTaskViewModel.Event.Products.Save -> {
                state.value = state.value.copy(
                    products = productsDialogState.value?.items.orEmpty()
                )
                productsDialogState.value = null
            }
        }
    }

    private fun onProductsCreatingEvent(event: ICreateTaskViewModel.Event.CreatingProducts) {
        when (event) {
            is ICreateTaskViewModel.Event.CreatingProducts.Dismiss -> {
                productsDialogState.value = productsDialogState.value?.copy(
                    creatingState = null
                )
            }
            is ICreateTaskViewModel.Event.CreatingProducts.OnAmountChanged -> {
                event.amount.toDoubleOrNull()?.let { amount ->
                    productsDialogState.value = productsDialogState.value?.copy(
                        creatingState = productsDialogState.value?.creatingState?.copy(
                            amount = amount
                        )?.copyWithUpdatedEnabled(productsDialogState.value)
                    )
                }
            }
            is ICreateTaskViewModel.Event.CreatingProducts.OnMeasureChanged -> {
                productsDialogState.value = productsDialogState.value?.copy(
                    creatingState = productsDialogState.value?.creatingState?.copy(
                        measure = event.measure
                    )?.copyWithUpdatedEnabled(productsDialogState.value)
                )
            }
            is ICreateTaskViewModel.Event.CreatingProducts.OnTitleChanged -> {
                productsDialogState.value = productsDialogState.value?.copy(
                    creatingState = productsDialogState.value?.creatingState?.copy(
                        title = event.title
                    )?.copyWithUpdatedEnabled(productsDialogState.value)
                )
            }
            is ICreateTaskViewModel.Event.CreatingProducts.Save -> {
                productsDialogState.value?.creatingState?.toProduct()?.let { product ->
                    val actualItems = productsDialogState.value?.items.orEmpty() + product

                    productsDialogState.value = productsDialogState.value?.copy(
                        items = actualItems
                            .distinctBy(TaskProduct::title)
                            .sortedBy(TaskProduct::title),
                        creatingState = null
                    )
                }
            }
        }
    }
}