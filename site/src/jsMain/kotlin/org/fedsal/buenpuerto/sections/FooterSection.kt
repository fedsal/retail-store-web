package org.fedsal.buenpuerto.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import org.fedsal.buenpuerto.components.AddToCartButton
import org.fedsal.buenpuerto.components.CounterButton
import org.jetbrains.compose.web.css.px

@Composable
fun FooterSection(
    onAddToCart: (quantity: Int) -> Unit =  {},
) {
    var quantity by remember { mutableStateOf(1) }
    Row(
        Modifier.padding(leftRight = 16.px, topBottom = 10.px).fillMaxWidth().height(70.px),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CounterButton(
            modifier = Modifier.fillMaxHeight(),
            count = quantity,
            onIncrement = {
                if (quantity < 1000) {
                    quantity++
                }
            },
            onDecrement = {
                if (quantity > 1) {
                    quantity--
                }
            }
        )
        Box(modifier = Modifier.width(8.px))
        AddToCartButton(onAddToCart = { onAddToCart(quantity) } )
    }
}