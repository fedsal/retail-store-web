package org.fedsal.buenpuerto.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Spacer
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
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import org.fedsal.buenpuerto.utils.FONT_FAMILY
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button

@Composable
fun OrderConfirmed(
    modifier: Modifier = Modifier,
    orderId: String,
    onDismiss: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Colors.White)
            .zIndex(3)
            .padding(topBottom = 40.px, leftRight = 20.px)
    ) {
        SpanText(
            "Pedido enviado",
            modifier = Modifier
                .fontSize(36.px)
                .fontWeight(FontWeight.SemiBold)
                .fillMaxWidth()
                .textAlign(TextAlign.Center)
                .margin(bottom = 30.px)
        )
        SpanText(
            "Acerquese al mostrador para continuar",
            modifier = Modifier
                .fontSize(24.px)
                .fillMaxWidth()
                .textAlign(TextAlign.Center)
                .margin(bottom = 30.px)
        )
        SpanText(
            "Código de orden:",
            modifier = Modifier
                .fontSize(20.px)
                .fillMaxWidth()
                .textAlign(TextAlign.Center)
                .margin(bottom = 10.px)
        )
        SpanText(
            orderId,
            modifier = Modifier
                .fontSize(40.px)
                .fillMaxWidth()
                .textAlign(TextAlign.Center)
                .margin(bottom = 30.px)
        )
        Spacer()
        Button(
            attrs = Modifier
                .height(50.px)
                .border(width = 0.px)
                .borderRadius(r = 10.px)
                .backgroundColor(Colors.Black)
                .color(Colors.White)
                .cursor(Cursor.Pointer)
                .fillMaxWidth()
                .onClick {
                    onDismiss()
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
                text = "Regresar".uppercase()
            )
        }
    }
}