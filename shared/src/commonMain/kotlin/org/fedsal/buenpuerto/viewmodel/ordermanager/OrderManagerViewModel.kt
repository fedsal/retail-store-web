package org.fedsal.buenpuerto.viewmodel.ordermanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.fedsal.buenpuerto.data.repository.OrderRepository

class OrderManagerViewModel(
    private val orderRepository: OrderRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(OrderManagerUiState())
    val uiState: StateFlow<OrderManagerUiState> get() = _uiState

    private val query = MutableStateFlow("")
    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1)


    init {
        loadOrders()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadOrders() = viewModelScope.launch {
        _uiState.value = OrderManagerUiState(isLoading = true)
        try {
            refreshTrigger
                .onStart { emit(Unit) }
                .flatMapLatest {
                    combine(
                        orderRepository.getAll(),
                        query
                    ) { orders, currentQuery ->
                        if (currentQuery.isBlank()) orders
                        else orders.filter {
                            it.clientName.contains(currentQuery, ignoreCase = true) ||
                                    it.id.contains(currentQuery, ignoreCase = true)
                        }
                    }
                }
                .collect { filteredOrders ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        orders = filteredOrders
                    )
                }
        } catch (e: Exception) {
            onError(e.message.toString())
        } finally {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    private fun onError(error: String) {
        _uiState.value = _uiState.value.copy(
            orders = uiState.value.orders, error = error
        )
    }

    fun clearErrors() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun searchOrders(query: String) {
        this.query.value = query
    }

    fun refresh() {
        viewModelScope.launch {
            refreshTrigger.emit(Unit)
        }
    }
}