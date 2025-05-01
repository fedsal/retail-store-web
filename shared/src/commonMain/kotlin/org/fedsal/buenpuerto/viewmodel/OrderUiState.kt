package org.fedsal.buenpuerto.viewmodel

import org.fedsal.buenpuerto.domain.model.Order

data class OrderUiState(
    val isLoading: Boolean = false,
    val order: Order = Order(),
    val errors: List<String>? = null
)