package org.fedsal.buenpuerto.data.datasource

import org.fedsal.buenpuerto.domain.model.Order

interface OrderLocalDataSource {
    suspend fun saveOrder(order: Order)
    suspend fun getOrder(): Order
    suspend fun updateOrder(order: Order)
}