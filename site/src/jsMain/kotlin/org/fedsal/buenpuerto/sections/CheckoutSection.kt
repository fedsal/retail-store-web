package org.fedsal.buenpuerto.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaXmark
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.fedsal.buenpuerto.components.ProductCard
import org.fedsal.buenpuerto.domain.model.OrderItem
import org.fedsal.buenpuerto.utils.FONT_FAMILY
import org.fedsal.buenpuerto.utils.Res
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun CheckoutSection(
    onMenuClosed: () -> Unit,
    products: List<OrderItem> = emptyList(),
    onIncrement: (OrderItem) -> Unit,
    onDecrement: (OrderItem) -> Unit,
    onRemove: (OrderItem) -> Unit
) {
    val scope = rememberCoroutineScope()
    val breakpoint = rememberBreakpoint()
    var opacity by remember { mutableStateOf(0.percent) }

    LaunchedEffect(breakpoint) {
        opacity = 100.percent
    }

    Column(
        modifier = Modifier.fillMaxSize().zIndex(2).opacity(opacity).backgroundColor(Colors.Red)
            .transition(Transition.of(property = "opacity", duration = 500.ms))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().scrollBehavior(ScrollBehavior.Smooth)
                .backgroundColor(Colors.White)
        ) {
            Row(
                modifier = Modifier.margin(bottom = 10.px).padding(leftRight = 20.px)
                    .background(Colors.Black).fillMaxWidth().height(70.px),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FaXmark(modifier = Modifier.cursor(Cursor.Pointer).margin(right = 20.px).onClick {
                    scope.launch {
                        opacity = 0.percent
                        delay(500)
                        onMenuClosed()
                    }
                }.color(Colors.White), size = IconSize.XL)
                Span(
                    attrs = Modifier.fontFamily(FONT_FAMILY).fontSize(20.px).color(Colors.White)
                        .toAttrs()
                ) { Text("Carrito de compras") }
            }
            Column(modifier = Modifier.fillMaxSize().padding(20.px)) {
                if (products.isEmpty()) {
                    EmptyProducts(modifier = Modifier.fillMaxWidth())
                } else {
                    ProductColumn(
                        modifier = Modifier.fillMaxWidth(),
                        products = products,
                        onIncrement,
                        onDecrement,
                        onRemove
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyProducts(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(10.px).borderRadius(10.px)
            .border(1.px, color = Color("#328FFF"), style = LineStyle.Solid),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(src = Res.Icons.INFO_CIRCLE_ICON)
        Span(attrs = Modifier.fontFamily(FONT_FAMILY).color(Color("#328FFF")).fontSize(18.px)
            .margin { left(10.px) }.toAttrs()
        ) {
            Text("El carrito esta vacio")
        }
    }
}

@Composable
fun ProductColumn(
    modifier: Modifier = Modifier,
    products: List<OrderItem>,
    onIncrement: (OrderItem) -> Unit,
    onDecrement: (OrderItem) -> Unit,
    onRemove: (OrderItem) -> Unit
) {
    Column(modifier) {
        products.forEachIndexed { index, product ->
            ProductCard(
                modifier = Modifier.fillMaxWidth(),
                product = product,
                onIncrement = onIncrement,
                onDecrement = onDecrement,
                onRemoveItem = onRemove
            )
            if (index != products.size - 1) {
                Box(
                    modifier.height(1.px).color(Colors.Black)
                ) { }
            }
        }
    }
}

