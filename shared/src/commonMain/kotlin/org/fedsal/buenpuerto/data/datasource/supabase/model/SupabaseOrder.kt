package org.fedsal.buenpuerto.data.datasource.supabase.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SupabaseOrder(
    val id: Int,
    @SerialName("cliente")
    val clientName: String,
    @SerialName("productos")
    val products: List<SupabaseOrderItem>,
    @SerialName("total")
    val total: Double,
)