package org.fedsal.buenpuerto.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class OrderItem(
    val product: Product,
    var quantity: Int,
)
