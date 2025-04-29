package org.fedsal.buenpuerto.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import org.fedsal.buenpuerto.utils.FONT_FAMILY
import org.fedsal.buenpuerto.utils.Res
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun CounterButton(
    modifier: Modifier = Modifier,
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
) {
    Row(
        modifier = modifier.padding(leftRight = 12.px).borderRadius(10.px).border(2.px, style = LineStyle.Solid ,color = Colors.Black).fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.onClick { onDecrement() }.margin { right(10.px) }.size(24.px).color(Colors.Black),
            src = Res.Icons.REMOVE_ITEM,
            alt = "Remover unidad"
        )
        P(
            attrs = Modifier
                .margin(0.px)
                .fontFamily(FONT_FAMILY)
                .fontSize(20.px)
                .toAttrs()
        ) {
            Text(count.toString())
        }
        Image(
            modifier = Modifier.onClick { onIncrement() }.margin { left(10.px) }.size(24.px).color(Colors.Black),
            src = Res.Icons.ADD_ITEM,
            alt = "Agregar unidad"
        )
    }
}