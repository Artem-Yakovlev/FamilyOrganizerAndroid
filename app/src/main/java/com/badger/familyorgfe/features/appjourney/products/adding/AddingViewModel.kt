package com.badger.familyorgfe.features.appjourney.products.adding

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.data.model.FamilyTask
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.ext.*
import com.badger.familyorgfe.features.appjourney.common.productbottomsheet.ProductBottomSheetState
import com.badger.familyorgfe.features.appjourney.products.adding.domain.AddProductUseCase
import com.badger.familyorgfe.features.appjourney.products.adding.model.UpdatableTask
import com.badger.familyorgfe.features.appjourney.products.adding.repository.IAddingRepository
import com.badger.familyorgfe.features.appjourney.products.fridge.fridgeitem.FridgeItem
import com.badger.familyorgfe.features.appjourney.tasks.alltasks.repository.IAllTasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AddingViewModel @Inject constructor(
    private val addingRepository: IAddingRepository,
    private val addProductUseCase: AddProductUseCase,
    private val allTasksRepository: IAllTasksRepository
) : BaseViewModel(), IAddingViewModel {

    private val isLoading = MutableStateFlow(false)
    override val successAdded = MutableStateFlow(false)

    override val items: StateFlow<List<FridgeItem>> = addingRepository
        .readyToAddingProducts
        .map { products -> products.map(Product::toFridgeItem) }
        .stateIn(viewModelScope(), started = SharingStarted.Lazily, initialValue = emptyList())

    override val expandedItemId = MutableStateFlow<Long?>(null)
    override val isAutoAdding = MutableStateFlow(false)

    override val deleteItemDialog = MutableStateFlow<FridgeItem?>(null)

    override val manualAddingState = MutableStateFlow<ProductBottomSheetState?>(null)
    override val doneEnabled = items.map(List<FridgeItem>::isNotEmpty)
        .stateIn(viewModelScope(), started = SharingStarted.Lazily, initialValue = false)

    override val editingState = MutableStateFlow<ProductBottomSheetState?>(null)

    override val updatableTasks = MutableStateFlow<List<UpdatableTask>?>(null)

    override fun onEvent(event: IAddingViewModel.Event) {
        when (event) {
            is IAddingViewModel.Event.Ordinal -> {
                onOrdinalEvent(event)
            }
            is IAddingViewModel.Event.ProductEvent -> {
                onBottomSheetEvent(
                    state = if (event.creating) {
                        manualAddingState
                    } else {
                        editingState
                    },
                    onAction = { product ->
                        if (event.creating) {
                            addingRepository.addProducts(product)
                        } else {
                            addingRepository.updateProduct(product)
                        }
                    },
                    event = event
                )
            }
            is IAddingViewModel.Event.TasksEvent -> {
                onTasksEvent(event)
            }
        }
    }

    private fun onOrdinalEvent(event: IAddingViewModel.Event.Ordinal) {
        when (event) {
            is IAddingViewModel.Event.Ordinal.OnAddClicked -> {
                manualAddingState.value = ProductBottomSheetState.createEmpty()
            }
            is IAddingViewModel.Event.Ordinal.OnEditClicked -> {
                editingState.value = ProductBottomSheetState.createFromFridgeItem(event.item)
                expandedItemId.value = null
            }
            is IAddingViewModel.Event.Ordinal.OnDoneClicked -> longRunning {
                if (!isLoading.value) {
                    isLoading.value = true

                    allTasksRepository.updateData()
                    val open = allTasksRepository.openTasks.firstOrNull().orEmpty()
                    val closed = allTasksRepository.closedTasks.firstOrNull().orEmpty()

                    val tasks = (open + closed)
                        .filter(FamilyTask::hasActiveProductList)
                        .map(UpdatableTask::fromFamilyTask)

                    isLoading.value = false
                    if (tasks.isNotEmpty()) {
                        updatableTasks.value = tasks
                    } else {
                        onTasksEvent(IAddingViewModel.Event.TasksEvent.OnContinue)
                    }
                }
            }
            is IAddingViewModel.Event.Ordinal.OnItemCollapsed -> {
                expandedItemId.value = null
            }
            is IAddingViewModel.Event.Ordinal.OnItemExpanded -> {
                expandedItemId.value = event.id
            }
            is IAddingViewModel.Event.Ordinal.DeleteItem -> {
                addingRepository.deleteProductById(event.item.id)
                deleteItemDialog.value = null
            }
            is IAddingViewModel.Event.Ordinal.DismissDeleteDialog -> {
                deleteItemDialog.value = null
            }
            is IAddingViewModel.Event.Ordinal.RequestDeleteItemDialog -> {
                deleteItemDialog.value = event.item
            }
            is IAddingViewModel.Event.Ordinal.OnBackClicked -> {
                clearData()
            }
            is IAddingViewModel.Event.Ordinal.OnAddLongClicked -> {
                isAutoAdding.value = !isAutoAdding.value
            }
            is IAddingViewModel.Event.Ordinal.OnProductsScanned -> {
                addingRepository.addProducts(*event.products.toTypedArray())
            }
        }
    }

    private fun onBottomSheetEvent(
        state: MutableStateFlow<ProductBottomSheetState?>,
        onAction: (Product) -> Unit,
        event: IAddingViewModel.Event.ProductEvent
    ) {
        when (event) {
            is IAddingViewModel.Event.ProductEvent.OnBottomSheetClose -> {
                state.value = null
            }
            is IAddingViewModel.Event.ProductEvent.OnTitleChanged -> {
                state.value = state.value?.copy(
                    title = event.title
                )
            }
            is IAddingViewModel.Event.ProductEvent.OnQuantityChanged -> {
                event.quantity.toDoubleOrNull()?.let { quantity ->
                    state.value = state.value?.copy(
                        quantity = quantity
                    )
                }
            }
            is IAddingViewModel.Event.ProductEvent.OnMeasureChanged -> {
                state.value = state.value?.copy(
                    measure = event.measure
                )
            }
            is IAddingViewModel.Event.ProductEvent.OnExpirationDateChanged -> {
                val expirationDate = event.date.convertToRealFutureDate()
                val expirationDays = expirationDate.toExpirationDays()

                state.value = state.value?.copy(
                    expirationDateString = event.date,
                    expirationDate = expirationDate,
                    expirationDaysString = expirationDays
                )
            }
            is IAddingViewModel.Event.ProductEvent.OnExpirationDaysChanged -> {
                val expirationDays = event.days.toIntOrNull()?.takeIf { it >= 0 }
                val expirationDate = expirationDays?.toExpirationDate()
                val expirationDateString = expirationDate?.toExpirationDateString().orEmpty()

                state.value = state.value?.copy(
                    expirationDateString = expirationDateString,
                    expirationDate = expirationDate,
                    expirationDaysString = expirationDays?.toString().orEmpty()
                )
            }
            is IAddingViewModel.Event.ProductEvent.OnActionClicked -> {
                val product = state.value?.createProduct() ?: return
                state.value = null
                onAction(product)
            }
        }
    }

    private fun onTasksEvent(
        event: IAddingViewModel.Event.TasksEvent
    ) {
        when (event) {
            is IAddingViewModel.Event.TasksEvent.Dismiss -> {
                updatableTasks.value = null
            }
            is IAddingViewModel.Event.TasksEvent.OnChecked -> {
                updatableTasks.value = updatableTasks.value?.map { task ->
                    if (task.taskId == event.taskId) {
                        task.copy(checked = event.checked)
                    } else {
                        task
                    }
                }
            }
            is IAddingViewModel.Event.TasksEvent.OnContinue -> longRunning {
                if (!isLoading.value) {
                    val argument = AddProductUseCase.Argument(
                        products = addingRepository.readyToAddingProducts.first(),
                        tasks = updatableTasks.value
                            ?.filter(UpdatableTask::checked)
                            ?.map(UpdatableTask::taskId).orEmpty()
                    )

                    isLoading.value = true
                    updatableTasks.value = null
                    val success = addProductUseCase(argument)

                    isLoading.value = success
                    successAdded.value = success
                }
            }
        }
    }

    override fun clearData() {
        addingRepository.clearStorage()
        expandedItemId.value = null
        deleteItemDialog.value = null
        manualAddingState.value = null
        editingState.value = null
        isLoading.value = false
    }
}