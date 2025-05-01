package org.fedsal.buenpuerto.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import org.fedsal.buenpuerto.data.datasource.OrderLocalDataSource
import org.fedsal.buenpuerto.data.repository.OrderRepository
import org.fedsal.buenpuerto.domain.model.Order
import org.fedsal.buenpuerto.domain.model.OrderItem
import org.fedsal.buenpuerto.domain.model.Product
import org.fedsal.buenpuerto.sections.CheckoutSection
import org.fedsal.buenpuerto.sections.FooterSection
import org.fedsal.buenpuerto.sections.HeaderSection
import org.fedsal.buenpuerto.sections.ImageCarouselSection
import org.fedsal.buenpuerto.sections.ProductPriceSection
import org.fedsal.buenpuerto.sections.ProductTitleSection
import org.fedsal.buenpuerto.viewmodel.OrderViewModel

@Page
@Composable
fun HomePage() {

    var cachedOrder by remember { mutableStateOf(Order()) }

    val viewModel = OrderViewModel(
        OrderRepository(
            object: OrderLocalDataSource {
                override suspend fun saveOrder(order: Order) {
                    cachedOrder = order
                }

                override suspend fun getOrder(): Order {
                    return cachedOrder
                }

                override suspend fun updateOrder(order: Order) {
                    cachedOrder = order
                    console.log("Order updated: $cachedOrder")
                }

            }
        )
    )

    val uiState by remember { viewModel.uiState }

    var menuOpened by remember { mutableStateOf(false) }
    val product = Product(
        code = "123",
        name = "Lorem ipsum dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
        imagesUrl = listOf("https://http2.mlstatic.com/D_NQ_NP_2X_661732-MLA82942925465_032025-F.webp", "https://example.com/image2.jpg"),
        price = 11122000.0
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            HeaderSection(onBagClicked = { menuOpened = true })
            ImageCarouselSection(product.imagesUrl)
            ProductTitleSection(product.name)
            ProductPriceSection(product.price)
            Spacer()
            FooterSection { quantity ->
                val orderItem = OrderItem(
                    product = product,
                    quantity = quantity
                )
                console.log("Added item")
                viewModel.addItem(orderItem)
            }
        }
        if (menuOpened) {
            CheckoutSection(onMenuClosed = { menuOpened = false }, order = uiState.order,
                onDecrement = { viewModel.removeItem(it) },
                onIncrement = { viewModel.addItem(it) },
                onRemove = { viewModel.deleteItem(it) },
                onPlaceOrder = { viewModel.sendOrder("Federico") }
            )
        }
    }
}



