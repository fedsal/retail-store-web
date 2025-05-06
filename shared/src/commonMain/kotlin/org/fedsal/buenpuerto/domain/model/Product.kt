package org.fedsal.buenpuerto.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int = 0,
    val code: String = "",
    val name: String = "",
    val imagesUrl: List<String> = emptyList(),
    val price: Double = 0.0
)
