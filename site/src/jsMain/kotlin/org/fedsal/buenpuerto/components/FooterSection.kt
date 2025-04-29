package org.fedsal.buenpuerto.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import org.jetbrains.compose.web.css.px

@Composable
fun FooterSection() {
    var quantity by remember { mutableStateOf(1) }
    Row(
        Modifier.padding(leftRight = 16.px, topBottom = 10.px).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CounterButton(
            count = quantity,
            onIncrement = { quantity++ },
            onDecrement = {
                if (quantity > 1) quantity--
                else quantity = 1
            }
        )
        Box(modifier = Modifier.width(8.px))
        AddToCartButton()
    }
}