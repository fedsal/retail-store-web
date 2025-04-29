package org.fedsal.buenpuerto.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import org.fedsal.buenpuerto.domain.model.Order
import org.fedsal.buenpuerto.domain.model.OrderItem
import org.fedsal.buenpuerto.domain.model.Product
import org.fedsal.buenpuerto.sections.CheckoutSection
import org.fedsal.buenpuerto.sections.FooterSection
import org.fedsal.buenpuerto.sections.HeaderSection
import org.fedsal.buenpuerto.sections.ImageCarouselSection
import org.fedsal.buenpuerto.sections.ProductPriceSection
import org.fedsal.buenpuerto.sections.ProductTitleSection

@Page
@Composable
fun HomePage() {
    var menuOpened by remember { mutableStateOf(false) }
    val product = Product(
        code = "123",
        name = "Lorem ipsum dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
        imagesUrl = listOf("https://http2.mlstatic.com/D_NQ_NP_2X_661732-MLA82942925465_032025-F.webp", "https://example.com/image2.jpg"),
        price = 22000.0
    )
    val order = Order(
        clientName = "Federico",
        products = listOf(
            OrderItem(
                product = product,
                quantity = 1
            ),
            OrderItem(
                product = Product(
                    code = "1234",
                    name = "Cafetera expresso 200ml",
                    imagesUrl = listOf("https://http2.mlstatic.com/D_NQ_NP_2X_661732-MLA82942925465_032025-F.webp"),
                    price = 23500.0
                ),
                quantity = 1
            )
        )
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
            FooterSection {
                // TODO add to cart
            }
        }
        if (menuOpened) {
            CheckoutSection(onMenuClosed = { menuOpened = false }, products = order.products,
                onDecrement = { },
                onIncrement = { },
                onRemove = { }
            )
        }
    }
}



