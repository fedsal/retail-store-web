package org.fedsal.buenpuerto.data.datasource

import kotlinx.coroutines.flow.Flow
import org.fedsal.buenpuerto.domain.model.Order
import org.fedsal.buenpuerto.domain.model.Product

interface OrderRemoteDataSource {
    suspend fun getAll(): Flow<List<Order>>
    suspend fun create(order: Order): Order
    suspend fun getProduct(code: String): Product
}