package org.fedsal.buenpuerto.domain.model

data class Product(
    val id: Int,
    val code: String,
    val name: String,
    val imagesUrl: List<String>,
    val price: Double
)
