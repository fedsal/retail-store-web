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
import org.fedsal.buenpuerto.components.FooterSection
import org.fedsal.buenpuerto.components.Header
import org.fedsal.buenpuerto.components.ImageCarousel
import org.fedsal.buenpuerto.sections.CheckoutSection
import org.fedsal.buenpuerto.components.ProductPrice
import org.fedsal.buenpuerto.components.ProductTitle

@Page
@Composable
fun HomePage() {
    var menuOpened by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Header(onBagClicked = { menuOpened = true })
            ImageCarousel()
            ProductTitle()
            ProductPrice()
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



