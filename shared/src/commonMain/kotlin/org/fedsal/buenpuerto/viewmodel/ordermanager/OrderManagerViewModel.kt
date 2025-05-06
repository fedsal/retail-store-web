package org.fedsal.buenpuerto.viewmodel.ordermanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.fedsal.buenpuerto.data.repository.OrderRepository

class OrderManagerViewModel(
    private val orderRepository: OrderRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(OrderManagerUiState())
    val uiState: StateFlow<OrderManagerUiState> get() = _uiState

    init {
        loadOrders()
    }

    private fun loadOrders() = viewModelScope.launch {
        _uiState.value = OrderManagerUiState(isLoading = true)
        try {
            orderRepository.getAll().collect { orders ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    orders = orders
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
}