package org.fedsal.buenpuerto.data.datasource.supabase

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.Realtime
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.fedsal.buenpuerto.data.datasource.OrderRemoteDataSource
import org.fedsal.buenpuerto.data.datasource.supabase.model.SupabaseOrder
import org.fedsal.buenpuerto.data.datasource.supabase.model.toDomain
import org.fedsal.buenpuerto.domain.model.Order

class SupabaseOrderRemoteDataSource : OrderRemoteDataSource {
    private val supabase = createSupabaseClient(
        supabaseUrl = "https://egybztihmrinszbpdrxt.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVneWJ6dGlobXJpbnN6YnBkcnh0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDYyMTE4MTksImV4cCI6MjA2MTc4NzgxOX0.SKsbdgrR5epVNXgyXobZdbJApQy8H5wfDeyLbPqfw3s"
    ) {
        install(Postgrest)
        install(Realtime)
    }

    override suspend fun getAll(): Flow<List<Order>> {
        return flow {
            while (true) {
                val orderList = retrieveOrder()
                emit(orderList)
                delay(30000)
            }
        }
    }

    private suspend fun retrieveOrder() =
        supabase.from("pedidos_con_productos").select().decodeList<SupabaseOrder>()
            .map { it.toDomain() }

    override suspend fun create(order: Order): Order {
        return Order()
    }
}
