package org.fedsal.buenpuerto.data.datasource.supabase.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SupabaseRawOrder(
    val id: Int,
    @SerialName("cliente")
    val clientName: String,
    @SerialName("total")
    val total: Double
)
