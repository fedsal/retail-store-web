package org.fedsal.buenpuerto.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.fedsal.buenpuerto.data.repository.OrderRepository
import org.fedsal.buenpuerto.domain.model.Order
import org.fedsal.buenpuerto.domain.model.OrderItem

class OrderViewModel(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(OrderUiState())
    val uiState: OrderUiState
        get() = _uiState.value

    private var order = Order()

    init {
        getOrder()
    }

    private fun getOrder() {
        viewModelScope.launch {
            try {
                order = orderRepository.getOrder()
                _uiState.value = OrderUiState(order = order)
            } catch (e: Exception) {
                onError(e.message.toString())
            }
        }
    }

    fun addItem(item: OrderItem) {
        viewModelScope.launch {
            val products = order.products
            val productExists = products.any { it.product.code == item.product.code }

            val updatedOrder = if (!productExists) {
                order.copy(products = products + item)
            } else {
                modifyQuantity(item.product.code, item.quantity)
            }

            updateOrder(updatedOrder)
        }
    }

    fun removeItem(item: OrderItem) {
        viewModelScope.launch {
            val products = order.products
            val productExists = products.any { it.product.code == item.product.code }

            val updatedOrder = if (!productExists) {
                order.copy(
                    products = products
                        .toMutableList()
                        .apply { removeAll { it.product.code == item.product.code } }
                )
            } else {
                modifyQuantity(item.product.code, -(item.quantity))
            }

            updateOrder(updatedOrder)
        }
    }

    private fun updateOrder(order: Order) {
        viewModelScope.launch {
            try {
                this@OrderViewModel.order = order
                orderRepository.updateOrder(order)
                _uiState.value = OrderUiState(order = order)
            } catch (e: Exception) {
                onError(e.message.toString())
            }
        }
    }

    private fun modifyQuantity(code: String, quantity: Int): Order = order.copy(
        products = order.products.map {
            if (it.product.code == code) {
                it.copy(quantity = it.quantity + quantity)
            } else {
                it
            }
        }
    )

    private fun onError(error: String) {
        _uiState.value = OrderUiState(
            order = uiState.order,
            errors = uiState.errors?.plus(error)
        )
    }

    fun clearErrors() {
        _uiState.value = OrderUiState(order = uiState.order)
    }
}