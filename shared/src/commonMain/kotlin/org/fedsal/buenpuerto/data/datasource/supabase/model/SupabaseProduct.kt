package org.fedsal.buenpuerto.data.datasource.supabase.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SupabaseProduct(
    @SerialName("codigo")
    val code: String,
    @SerialName("nombre")
    val name: String,
    @SerialName("imagenes")
    val imagesUrl: List<String>,
    @SerialName("precio")
    val price: Double
)