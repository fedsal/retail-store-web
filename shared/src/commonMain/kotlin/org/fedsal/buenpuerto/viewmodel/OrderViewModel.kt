package org.fedsal.buenpuerto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.fedsal.buenpuerto.data.repository.OrderRepository
import org.fedsal.buenpuerto.domain.model.Order
import org.fedsal.buenpuerto.domain.model.OrderItem

class OrderViewModel(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState>
        get() = _uiState

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

    fun sendOrder(clientName: String) {
        viewModelScope.launch {
            try {
                orderRepository.createOrder(order.copy(
                    clientName = clientName
                ))
                orderRepository.getAll().collect {
                    console.log("Order sent: $it")
                }
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

            try {
                if (!productExists) {
                    throw IllegalArgumentException("Product not found in order")
                }
                val updatedOrder = modifyQuantity(item.product.code, -(item.quantity))

                updateOrder(updatedOrder)
            } catch (e: Exception) {
                onError(e.message.toString())
            }
        }
    }

    fun deleteItem(item: OrderItem) {
        val products = order.products
        val productExists = products.any { it.product.code == item.product.code }

        val updatedOrder = if (!productExists) {
            order.copy(
                products = products
                    .toMutableList()
                    .apply { removeAll { it.product.code == item.product.code } }
            )
        } else {
            order
        }
    }

    private fun updateOrder(order: Order) {
        viewModelScope.launch {
            try {
                val updatedOrder = order.copy(
                    total = order.products.sumOf { it.product.price * it.quantity }
                )
                this@OrderViewModel.order = updatedOrder
                orderRepository.updateOrder(updatedOrder)
                _uiState.value = OrderUiState(order = updatedOrder)
                console.log("Order updated: $uiState")
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
        console.log(error)
        _uiState.value = OrderUiState(
            order = uiState.value.order,
            errors = uiState.value.errors?.plus(error)
        )
    }

    fun clearErrors() {
        _uiState.value = OrderUiState(order = uiState.value.order)
    }
}