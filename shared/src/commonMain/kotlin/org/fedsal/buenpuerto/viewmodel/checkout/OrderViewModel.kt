package org.fedsal.buenpuerto.viewmodel.checkout

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

    fun initializeData(productCode: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                order = orderRepository.getOrder()
                val product = orderRepository.getProduct(productCode)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    order = order,
                    product = product
                )
            } catch (e: Exception) {
                onError(e.message.toString())
            }
        }
    }

    fun sendOrder(clientName: String) {
        viewModelScope.launch {
            try {
                val order = orderRepository.createOrder(
                    order.copy(
                        clientName = clientName
                    )
                )
                _uiState.value = _uiState.value.copy(
                    order = order,
                    isLoading = false,
                    isOrderConfirmed = true
                )
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

        val updatedOrder = if (productExists) {
            order.copy(
                products = products.toMutableList()
                    .apply { removeAll { it.product.code == item.product.code } })
        } else {
            order
        }
        updateOrder(updatedOrder)
    }

    private fun updateOrder(order: Order) {
        viewModelScope.launch {
            try {
                val updatedOrder =
                    order.copy(total = order.products.sumOf { it.product.price * it.quantity })
                this@OrderViewModel.order = updatedOrder
                orderRepository.updateOrder(updatedOrder)
                _uiState.value = _uiState.value.copy(order = updatedOrder)
            } catch (e: Exception) {
                onError(e.message.toString())
            }
        }
    }

    private fun modifyQuantity(code: String, quantity: Int): Order {
        return if ((order.products.find { it.product.code == code }?.quantity
                ?: 0) + quantity <= 0
        ) {
            return order.copy(
                products = order.products.toMutableList()
                    .apply { removeAll { it.product.code == code } })
        } else {
            order.copy(products = order.products.map {
                if (it.product.code == code) {
                    it.copy(quantity = it.quantity + quantity)
                } else {
                    it
                }
            })
        }
    }

    private fun onError(error: String) {
        _uiState.value = _uiState.value.copy(
            order = uiState.value.order, errors = uiState.value.errors?.plus(error)
        )
    }

    fun clearErrors() {
        _uiState.value = OrderUiState(order = uiState.value.order)
    }

    fun clearOrder() {
        viewModelScope.launch {
            try {
                val emptyOrder = Order()
                orderRepository.updateOrder(emptyOrder)
                _uiState.value = OrderUiState(product = uiState.value.product)
                order = emptyOrder
            } catch (e: Exception) {
                onError(e.message.toString())
            }
        }
    }
}