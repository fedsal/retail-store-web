package org.fedsal.buenpuerto.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.stevdza.san.kotlinbs.forms.BSInput
import com.stevdza.san.kotlinbs.models.InputSize
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
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
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaXmark
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import org.fedsal.buenpuerto.utils.FONT_FAMILY
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button

@Composable
fun OrderPlacementDialog(
    modifier: Modifier = Modifier,
    onContinue: (clientName: String) -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.background(Colors.White).fillMaxHeight(30.percent).width(90.percent)
            .borderRadius(10.px).padding(30.px)
    ) {
        Row(
            Modifier.fillMaxWidth().margin { bottom(20.px) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            FaXmark(modifier = Modifier.cursor(Cursor.Pointer).margin(right = 20.px).onClick {
                onDismiss()
            }.color(Colors.Black), size = IconSize.XL)
            SpanText(
                "Comprar carrito",
                modifier = Modifier.fillMaxWidth().textAlign(TextAlign.Start).fontWeight(
                    FontWeight.Bold
                ).color(Colors.Black).fontSize(20.px)
            )
        }
        var inputValue by remember { mutableStateOf("") }
        BSInput(
            modifier = Modifier.fillMaxWidth().height(60.px).margin(bottom = 30.px),
            size = InputSize.Large,
            value = inputValue,
            placeholder = "Escriba su nombre",
            onValueChange = {
                inputValue = it
            }
        )
        Button(
            attrs = Modifier
                .height(50.px)
                .border(width = 0.px)
                .borderRadius(r = 10.px)
                .backgroundColor(if (inputValue.isNotBlank()) Colors.Black else Colors.LightGray)
                .color(Colors.White)
                .cursor(Cursor.Pointer)
                .fillMaxWidth()
                .onClick {
                    if (inputValue.isBlank()) return@onClick
                    onContinue(inputValue)
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
                text = "Continuar".uppercase()
            )
        }
    }
}
