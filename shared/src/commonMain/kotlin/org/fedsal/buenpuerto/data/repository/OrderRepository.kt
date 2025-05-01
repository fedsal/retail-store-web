package org.fedsal.buenpuerto.data.repository

import org.fedsal.buenpuerto.data.datasource.OrderLocalDataSource
import org.fedsal.buenpuerto.domain.model.Order

class OrderRepository(
    private val localDataSource: OrderLocalDataSource
) {
    suspend fun getOrder(): Order = localDataSource.getOrder()
    suspend fun saveOrder(order: Order) = localDataSource.saveOrder(order)
    suspend fun updateOrder(order: Order) = localDataSource.updateOrder(order)
}