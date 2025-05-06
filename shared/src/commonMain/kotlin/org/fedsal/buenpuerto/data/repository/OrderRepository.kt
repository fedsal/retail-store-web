package org.fedsal.buenpuerto.data.repository

import org.fedsal.buenpuerto.data.datasource.OrderLocalDataSource
import org.fedsal.buenpuerto.data.datasource.OrderRemoteDataSource
import org.fedsal.buenpuerto.domain.model.Order
import org.fedsal.buenpuerto.domain.model.Product

class OrderRepository(
    private val localDataSource: OrderLocalDataSource,
    private val remoteDataSource: OrderRemoteDataSource
) {
    suspend fun getOrder(): Order = localDataSource.getOrder()
    suspend fun saveOrder(order: Order) = localDataSource.saveOrder(order)
    suspend fun updateOrder(order: Order) = localDataSource.updateOrder(order)
    // Remote behaviour
    suspend fun createOrder(order: Order) = remoteDataSource.create(order)
    suspend fun getAll() = remoteDataSource.getAll()
    suspend fun getProduct(code: String): Product = remoteDataSource.getProduct(code)
}