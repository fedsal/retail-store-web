package org.fedsal.buenpuerto.data.datasource.supabase.model

import org.fedsal.buenpuerto.domain.model.Order
import org.fedsal.buenpuerto.domain.model.OrderItem
import org.fedsal.buenpuerto.domain.model.Product

fun SupabaseOrder.toDomain() = Order(
    id = id.toString(),
    clientName = clientName,
    products = products.map { it.toDomain() },
    total = total,
)

fun SupabaseOrderItem.toDomain() = OrderItem(
    product = product.toDomain(),
    quantity = quantity,
)

fun SupabaseProduct.toDomain() = Product(
    code = code,
    name = name,
    price = price,
    imagesUrl = imagesUrl,
)