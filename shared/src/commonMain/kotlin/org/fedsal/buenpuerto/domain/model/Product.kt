package org.fedsal.buenpuerto.domain.model

data class Product(
    val code: String,
    val name: String,
    val imagesUrl: List<String>,
    val price: Double
)
