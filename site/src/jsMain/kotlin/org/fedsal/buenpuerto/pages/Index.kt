package org.fedsal.buenpuerto.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import org.fedsal.buenpuerto.components.AddToCartButton
import org.fedsal.buenpuerto.components.Header
import org.fedsal.buenpuerto.components.ProductImages
import org.fedsal.buenpuerto.components.ProductTitle
import org.fedsal.buenpuerto.utils.FONT_FAMILY
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
fun HomePage() {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Header()
        ProductImages()
        ProductTitle()
        ProductPrice()
        Spacer()
        Box(
            Modifier.padding(leftRight = 16.px, topBottom = 10.px).fillMaxWidth()
        ) {
            AddToCartButton(
                modifier = Modifier.width(80.px)
            )
        }
    }
}

@Composable
fun ProductPrice() {
    Box(Modifier.padding(leftRight = 16.px, topBottom = 10.px).fillMaxWidth()) {
        P(
            attrs = Modifier
                .fillMaxWidth()
                .fontFamily(FONT_FAMILY)
                .fontSize(30.px)
                .fontWeight(FontWeight.Normal)
                .color(Colors.Black)
                .margin(0.px)
                .toAttrs()
        ) {
            Text("$ 20.000")
        }
    }
}


