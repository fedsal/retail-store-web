package org.fedsal.buenpuerto.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: String = "",
    val clientName: String = "",
    val products: List<OrderItem> = emptyList(),
    val total: Double = 0.0,
)
