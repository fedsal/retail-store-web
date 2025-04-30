package org.fedsal.buenpuerto.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TextOverflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.compose.ui.modifiers.objectFit
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.textOverflow
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaTrashCan
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import org.fedsal.buenpuerto.domain.model.OrderItem
import org.fedsal.buenpuerto.utils.FONT_FAMILY
import org.fedsal.buenpuerto.utils.Res
import org.fedsal.buenpuerto.utils.formatDecimal
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.px

@Composable
fun ProductCard(
    modifier: Modifier,
    product: OrderItem,
    onIncrement: (OrderItem) -> Unit = {},
    onDecrement: (OrderItem) -> Unit = {},
    onRemoveItem: (OrderItem) -> Unit = {},
) {
    Row(
        modifier = modifier.height(80.px).fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Image(
            src = product.product.imagesUrl.first(),
            modifier = Modifier
                .height(80.px)
                .width(80.px)
                .borderRadius(10.px)
                .objectFit(ObjectFit.Cover)
                .onClick { onRemoveItem(product) }
                .background(Color(Res.Colors.IMAGE_BACKGROUND)),
            alt = "Imagen del producto",
        )
        Column(
            modifier = Modifier.margin { left(10.px) }.fillMaxSize()
        ) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                SpanText(
                    modifier = Modifier
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px)
                        .lineHeight(18.px)
                        .maxHeight(36.px)
                        .fillMaxWidth()
                        .overflow(Overflow.Hidden)
                        .textOverflow(TextOverflow.Ellipsis),
                    text = product.product.name,
                )
                Spacer()
                FaTrashCan(
                    size = IconSize.XL,
                    modifier = Modifier.color(Colors.Black).margin { left(10.px) }
                )
            }
            Spacer()
            Row(
                Modifier.fillMaxWidth().margin {
                    left(10.px)
                    top(20.px)
                },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Bottom
            ) {
                CounterButton(
                    modifier = Modifier.width(110.px),
                    count = product.quantity,
                    onIncrement = { onIncrement(product) },
                    onDecrement = { onDecrement(product) }
                )
                Spacer()
                SpanText(
                    modifier = Modifier
                        .fontFamily(FONT_FAMILY)
                        .fontSize(18.px)
                        .fontWeight(FontWeight.Bold)
                        .color(Colors.Black),
                    text = "$ ${(product.product.price * product.quantity).formatDecimal()}"
                )
            }
        }
    }
}

