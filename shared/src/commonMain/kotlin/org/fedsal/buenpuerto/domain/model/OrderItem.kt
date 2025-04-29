package org.fedsal.buenpuerto.domain.model

data class OrderItem(
    val product: Product,
    var quantity: Int,
)
