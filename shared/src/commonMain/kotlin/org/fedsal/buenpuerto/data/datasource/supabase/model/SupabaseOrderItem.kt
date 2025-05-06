package org.fedsal.buenpuerto.data.datasource.supabase.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SupabaseOrderItem(
    @SerialName("cantidad")
    val quantity: Int,
    @SerialName("producto")
    val product: SupabaseProduct
)