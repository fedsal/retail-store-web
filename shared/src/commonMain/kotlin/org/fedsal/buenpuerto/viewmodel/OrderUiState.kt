package org.fedsal.buenpuerto.viewmodel

import org.fedsal.buenpuerto.domain.model.Order
import org.fedsal.buenpuerto.domain.model.Product

data class OrderUiState(
    val product: Product = Product(),
    val isLoading: Boolean = false,
    val order: Order = Order(),
    val errors: List<String>? = null
)