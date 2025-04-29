package org.fedsal.buenpuerto.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import org.fedsal.buenpuerto.utils.FONT_FAMILY
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun ProductTitle() {
    Box(
        Modifier.padding(leftRight = 16.px).margin(top = 10.px).height(60.px).fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        P(
            attrs = Modifier
                .fillMaxWidth()
                .fontFamily(FONT_FAMILY)
                .fontSize(30.px)
                .fontWeight(FontWeight.Bold)
                .color(Colors.Black)
                .margin(0.px)
                .toAttrs()
        ) {
            Text("Cafetera italiana 600ml")
        }
    }
}