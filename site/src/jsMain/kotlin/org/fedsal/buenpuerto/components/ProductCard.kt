package org.fedsal.buenpuerto.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.TextOverflow
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.compose.ui.modifiers.objectFit
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.textOverflow
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaTrashCan
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import org.fedsal.buenpuerto.domain.model.OrderItem
import org.fedsal.buenpuerto.utils.FONT_FAMILY
import org.fedsal.buenpuerto.utils.Res
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
        modifier = modifier.height(80.px),
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
            modifier = Modifier.margin { left(10.px) }.fillMaxHeight()
        ) {
            SpanText(
                modifier = Modifier
                    .fontFamily(FONT_FAMILY)
                    .fontSize(16.px)
                    .textOverflow(TextOverflow.Ellipsis)
                    .maxHeight(30.px),
                text = product.product.name,
            )
            Spacer()
            CounterButton(
                modifier = Modifier.width(110.px),
                count = product.quantity,
                onIncrement = { onIncrement(product) },
                onDecrement = { onDecrement(product) }
            )
        }
        Spacer()
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.End
        ) {
            FaTrashCan(
                size = IconSize.XL,
                modifier = Modifier.color(Colors.Black)
            )
            Spacer()
            SpanText(
                modifier = Modifier
                    .fontFamily(FONT_FAMILY)
                    .fontSize(22.px)
                    .fontWeight(FontWeight.Bold)
                    .color(Colors.Black),
                text = "$ ${product.product.price * product.quantity}"
            )
        }
    }
}