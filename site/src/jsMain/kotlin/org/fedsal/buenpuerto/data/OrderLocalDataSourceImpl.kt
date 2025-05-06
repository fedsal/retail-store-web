package org.fedsal.buenpuerto.data

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf

import org.fedsal.buenpuerto.data.datasource.OrderLocalDataSource
import org.fedsal.buenpuerto.domain.model.Order

class OrderLocalDataSourceImpl: OrderLocalDataSource {
    private val store: KStore<Order> = storeOf("order")

    override suspend fun saveOrder(order: Order) {
        store.set(order)
        console.log("Carrito guardado: $order")
    }

    override suspend fun getOrder(): Order {
        console.log("Carrito recuperado: ${store.get()}")
        return store.get() ?: Order()
    }

    override suspend fun updateOrder(order: Order) {
        store.set(order)
        console.log("Carrito guardado: $order")
    }
}