package org.fedsal.buenpuerto.domain.model

data class Order(
    val id: String = "",
    val clientName: String,
    val products: List<OrderItem>,
    val total: Double,
)
