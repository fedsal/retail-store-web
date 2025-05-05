package org.fedsal.buenpuerto.data.datasource

import kotlinx.coroutines.flow.Flow
import org.fedsal.buenpuerto.domain.model.Order

interface OrderRemoteDataSource {
    suspend fun getAll(): Flow<List<Order>>
    suspend fun create(order: Order): Order
}