package com.badger.familyorgfe.features.appjourney.adding

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.ext.convertToRealFutureDate
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.ext.toFridgeItem
import com.badger.familyorgfe.ext.viewModelScope
import com.badger.familyorgfe.features.appjourney.adding.domain.AddProductUseCase
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import org.threeten.bp.Duration
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddingViewModel @Inject constructor(
    private val addProductUseCase: AddProductUseCase
) : BaseViewModel(), IAddingViewModel {

    private val products = MutableStateFlow<List<Product>>(emptyList())
    private val isLoading = MutableStateFlow(false)
    override val successAdded = MutableStateFlow(false)

    override val items: StateFlow<List<FridgeItem>> = products
        .map { products -> products.map(Product::toFridgeItem) }
        .stateIn(viewModelScope(), started = SharingStarted.Lazily, initialValue = emptyList())

    override val expandedItemId = MutableStateFlow<String?>(null)
    override val deleteItemDialog = MutableStateFlow<FridgeItem?>(null)
    override val manualAddingState = MutableStateFlow<IAddingViewModel.ManualAddingState?>(null)
    override val doneEnabled = items.map(List<FridgeItem>::isNotEmpty)
        .stateIn(viewModelScope(), started = SharingStarted.Lazily, initialValue = false)

    override fun onEvent(event: IAddingViewModel.Event) {
        when (event) {
            is IAddingViewModel.Event.OnAddClicked -> {
                manualAddingState.value = IAddingViewModel.ManualAddingState.createEmpty()
            }
            is IAddingViewModel.Event.OnDoneClicked -> longRunning {
                if (!isLoading.value) {
                    isLoading.value = true
                    val success = addProductUseCase(products.value)
                    isLoading.value = success
                    successAdded.value = success
                }
            }
            is IAddingViewModel.Event.OnItemCollapsed -> {
                expandedItemId.value = null
            }
            is IAddingViewModel.Event.OnItemExpanded -> {
                expandedItemId.value = event.id
            }
            is IAddingViewModel.Event.DeleteItem -> {
                products.value = products.value.filter { it.id != event.item.id }
                deleteItemDialog.value = null
            }
            is IAddingViewModel.Event.DismissDeleteDialog -> {
                deleteItemDialog.value = null
            }
            is IAddingViewModel.Event.RequestDeleteItemDialog -> {
                deleteItemDialog.value = event.item
            }
            is IAddingViewModel.Event.OnBackClicked -> {
                clearData()
            }
            is IAddingViewModel.Event.OnBottomSheetClose -> {
                manualAddingState.value = null
            }
            is IAddingViewModel.Event.OnManualAddingTitleChanged -> {
                manualAddingState.value = manualAddingState.value?.copy(
                    title = event.title
                )
            }
            is IAddingViewModel.Event.OnManualAddingQuantityChanged -> {
                event.quantity.toDoubleOrNull()?.let { quantity ->
                    manualAddingState.value = manualAddingState.value?.copy(
                        quantity = quantity
                    )
                }
            }
            is IAddingViewModel.Event.OnManualAddingMeasureChanged -> {
                manualAddingState.value = manualAddingState.value?.copy(
                    measure = event.measure
                )
            }
            is IAddingViewModel.Event.OnManualAddingExpirationDateChanged -> {
                val expirationDate = event.date.convertToRealFutureDate()
                val expirationDays = try {
                    if (LocalDate.now() == expirationDate) {
                        ZERO
                    } else {
                        Duration.between(
                            LocalDate.now().atStartOfDay(),
                            expirationDate?.atStartOfDay()
                        ).toDays().toString()
                    }
                } catch (e: Exception) {
                    null
                }
                manualAddingState.value = manualAddingState.value?.copy(
                    expirationDateString = event.date,
                    expirationDate = expirationDate,
                    expirationDaysString = expirationDays
                )
            }
            is IAddingViewModel.Event.OnManualAddingExpirationDaysChanged -> {
                val expirationDays = event.days.toIntOrNull()?.takeIf { it >= 0 }
                val expirationDate = expirationDays?.let { expDays ->
                    LocalDate.now().plusDays(expDays.toLong())
                }
                val expirationDateString = try {
                    expirationDate?.format(dateFormat).orEmpty()
                } catch (e: Exception) {
                    ""
                }

                manualAddingState.value = manualAddingState.value?.copy(
                    expirationDateString = expirationDateString,
                    expirationDate = expirationDate,
                    expirationDaysString = expirationDays?.toString().orEmpty()
                )
            }
            is IAddingViewModel.Event.OnCreateClicked -> {
                val product = manualAddingState.value?.createProduct() ?: return
                manualAddingState.value = null
                products.value = (products.value + product).sortedBy(Product::name)
            }
        }
    }

    override fun clearData() {
        products.value = emptyList()
        expandedItemId.value = null
        deleteItemDialog.value = null
        manualAddingState.value = null
        isLoading.value = false
    }

    companion object {
        private const val ZERO = "0"
        private const val DATE_FORMAT = "dd.MM.yyyy"
        private val dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT)
    }
}