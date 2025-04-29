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
        name = "Product Name",
        imagesUrl = listOf("https://example.com/image1.jpg", "https://example.com/image2.jpg"),
        price = 2000.0
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
            FooterSection(
                onDecrement = {  },
                onIncrement = {  },
                onAddToCart = {  }
            )
        }
        if (menuOpened) {
            CheckoutSection(onMenuClosed = { menuOpened = false }, products = emptyList(),
                onDecrement = {  },
                onIncrement = {  },
                onRemove = {  }
            )
        }
    }
}



