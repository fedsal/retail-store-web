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
    id = id,
    code = code,
    name = name,
    price = price,
    imagesUrl = imagesUrl,
)


fun Order.toSupabaseOrder() = SupabaseOrder(
    id = id.toInt(),
    clientName = clientName,
    products = products.map { it.toSupabaseOrderItem() },
    total = total,
)

fun OrderItem.toSupabaseOrderItem() = SupabaseOrderItem(
    product = product.toSupabaseProduct(),
    quantity = quantity,
)

fun Product.toSupabaseProduct() = SupabaseProduct(
    id = id,
    code = code,
    name = name,
    price = price,
    imagesUrl = imagesUrl,
)

fun SupabaseRawOrder.toDomain() = Order(
    id = id.toString(),
    clientName = clientName,
    products = emptyList(),
    total = total,
)