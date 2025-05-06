package org.fedsal.buenpuerto.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TextOverflow
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textOverflow
import com.varabyte.kobweb.silk.components.text.SpanText
import org.fedsal.buenpuerto.utils.FONT_FAMILY
import org.jetbrains.compose.web.css.px

@Composable
fun ProductTitleSection(
    title: String
) {
    Box(
        Modifier.padding(leftRight = 16.px).margin(top = 10.px).fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        SpanText(
            modifier = Modifier
                .fillMaxSize()
                .fontFamily(FONT_FAMILY)
                .fontSize(26.px)
                .overflow(Overflow.Hidden)
                .textOverflow(TextOverflow.Ellipsis)
                .fontWeight(FontWeight.Bold)
                .lineHeight(30.px)
                .maxHeight(90.px)
                .color(Colors.Black),
            text = title
        )
    }
}