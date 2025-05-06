package org.fedsal.buenpuerto.data.datasource.supabase

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.realtime.Realtime
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.addJsonObject
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.fedsal.buenpuerto.data.datasource.OrderRemoteDataSource
import org.fedsal.buenpuerto.data.datasource.supabase.model.SupabaseOrder
import org.fedsal.buenpuerto.data.datasource.supabase.model.SupabaseProduct
import org.fedsal.buenpuerto.data.datasource.supabase.model.SupabaseRawOrder
import org.fedsal.buenpuerto.data.datasource.supabase.model.toDomain
import org.fedsal.buenpuerto.domain.model.Order
import org.fedsal.buenpuerto.domain.model.Product

class SupabaseOrderRemoteDataSource : OrderRemoteDataSource {
    private val supabase = createSupabaseClient(SUPABASE_URL, SUPABASE_SECRET) {
        install(Postgrest)
        install(Realtime)
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    override suspend fun getAll(): Flow<List<Order>> {
        return flow {
            while (true) {
                val orderList = retrieveOrder()
                emit(orderList)
                delay(POLLING_DELAY)
            }
        }
    }

    private suspend fun retrieveOrder() =
        supabase.from(ORDER_PRODUCTS_TABLE).select().decodeList<SupabaseOrder>()
            .map { it.toDomain() }

    override suspend fun create(order: Order): Order {
        val products = buildJsonArray {
            order.products.forEach { item ->
                addJsonObject {
                    put(PRODUCT_ID_KEY, item.product.id)
                    put(QUANTITY_KEY, item.quantity)
                }
            }
        }
        val newOrder = supabase.postgrest.rpc(
            function = CREATE_ORDER_FUNCTION,
            parameters = buildJsonObject {
                put(CLIENT_NAME, order.clientName)
                put(PRODUCTS, products)
                put(TOTAL, order.total)
            }
        ).decodeAs<SupabaseRawOrder>()
        return newOrder.toDomain()
    }

    override suspend fun getProduct(code: String): Product {
        val product = supabase.from("productos").select {
                filter {
                    eq("codigo", code)
                }
            }
            .decodeList<SupabaseProduct>()
            .firstOrNull()
        return product?.toDomain() ?: throw Exception("Product not found")
    }

    companion object {
        private const val CREATE_ORDER_FUNCTION = "crear_pedido_con_productos"
        private const val CLIENT_NAME = "cliente_nombre"
        private const val PRODUCTS = "productos"
        private const val TOTAL = "total"
        private const val PRODUCT_ID_KEY = "producto_id"
        private const val QUANTITY_KEY = "cantidad"

        private const val ORDER_PRODUCTS_TABLE = "pedidos_con_productos"
        private const val POLLING_DELAY = 30000L

        private const val SUPABASE_URL = "https://egybztihmrinszbpdrxt.supabase.co"
        private const val SUPABASE_SECRET = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVneWJ6dGlobXJpbnN6YnBkcnh0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDYyMTE4MTksImV4cCI6MjA2MTc4NzgxOX0.SKsbdgrR5epVNXgyXobZdbJApQy8H5wfDeyLbPqfw3s"
    }
}
