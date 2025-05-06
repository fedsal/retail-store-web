package org.fedsal.buenpuerto.viewmodel.ordermanager

import org.fedsal.buenpuerto.domain.model.Order

data class OrderManagerUiState(
    val isLoading: Boolean = false,
    val orders: List<Order> = emptyList(),
    val error: String? = null,
)
