package org.fedsal.buenpuerto.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
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
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.leftRight
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaXmark
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.fedsal.buenpuerto.components.ProductCard
import org.fedsal.buenpuerto.domain.model.Order
import org.fedsal.buenpuerto.domain.model.OrderItem
import org.fedsal.buenpuerto.utils.FONT_FAMILY
import org.fedsal.buenpuerto.utils.Res
import org.fedsal.buenpuerto.utils.formatDecimal
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Button

@Composable
fun CheckoutSection(
    onMenuClosed: () -> Unit,
    order: Order,
    onIncrement: (OrderItem) -> Unit,
    onDecrement: (OrderItem) -> Unit,
    onRemove: (OrderItem) -> Unit,
    onPlaceOrder: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val breakpoint = rememberBreakpoint()
    var opacity by remember { mutableStateOf(0.percent) }

    LaunchedEffect(breakpoint) {
        opacity = 100.percent
    }

    Column(
        modifier = Modifier.fillMaxSize().zIndex(5).opacity(opacity).backgroundColor(Colors.Red)
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
                SpanText(
                    modifier = Modifier.fontFamily(FONT_FAMILY).fontSize(20.px).color(Colors.White),
                    text = "Carrito de compras"
                )
            }
            Column(modifier = Modifier.fillMaxSize()) {
                if (order.products.isEmpty()) {
                    EmptyProducts()
                } else {
                    ProductColumn(
                        modifier = Modifier.fillMaxWidth()
                            .padding(leftRight = 20.px, topBottom = 20.px).maxHeight(70.vh)
                            .overflow { y(Overflow.Scroll) },
                        products = order.products,
                        onIncrement,
                        onDecrement,
                        onRemove
                    )
                }
                Spacer()
                Column(modifier = Modifier.fillMaxWidth().padding(20.px)) {
                    Row(
                        Modifier.fillMaxWidth().margin { bottom(20.px) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SpanText(
                            modifier = Modifier.fontSize(26.px).fontFamily(FONT_FAMILY)
                                .fontWeight(FontWeight.Bold).color(Colors.Black),
                            text = "Total:"
                        )
                        Spacer()
                        SpanText(
                            modifier = Modifier.fontSize(26.px).fontFamily(FONT_FAMILY)
                                .color(Colors.Black),
                            text = "$ ${order.total.formatDecimal()}"
                        )
                    }
                    Button(
                        attrs = Modifier
                            .height(60.px)
                            .border(width = 0.px)
                            .borderRadius(r = 10.px)
                            .backgroundColor(if (order.products.isNotEmpty()) Colors.Black else Colors.LightGray)
                            .color(Colors.White)
                            .cursor(Cursor.Pointer)
                            .fillMaxWidth()
                            .onClick {
                                if (order.products.isEmpty()) return@onClick
                                onPlaceOrder()
                            }
                            .toAttrs()
                    ) {
                        SpanText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fontFamily(FONT_FAMILY)
                                .fontSize(16.px)
                                .fontWeight(FontWeight.Bold)
                                .color(Colors.White),
                            text = "Enviar pedido".uppercase()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyProducts(
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxWidth().padding { leftRight(20.px) }, contentAlignment = Alignment.Center) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.px)
                .borderRadius(10.px)
                .border(
                    width = 1.px,
                    color = Color(Res.Colors.HINT_COLOR),
                    style = LineStyle.Solid
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(src = Res.Icons.INFO_CIRCLE_ICON)
            SpanText(
                modifier = Modifier.fontFamily(FONT_FAMILY).color(Color(Res.Colors.HINT_COLOR))
                    .fontSize(18.px)
                    .lineHeight(20.px)
                    .maxHeight(40.px)
                    .margin { left(10.px) },
                text = "El carrito esta vacio"
            )
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
                    Modifier.height(1.px)
                        .fillMaxWidth()
                        .background(Colors.Black)
                        .margin {
                            bottom(30.px)
                            top(20.px)
                        }
                )
            }
        }
    }
}

