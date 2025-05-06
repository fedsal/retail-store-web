package org.fedsal.buenpuerto.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.silk.components.graphics.Image
import org.fedsal.buenpuerto.utils.Res
import org.jetbrains.compose.web.css.px

@Composable
fun HeaderSection(onBagClicked: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxSize().height(65.px).background(Colors.Black).padding(10.px),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            modifier = Modifier.size(40.px),
            src = Res.Images.LOGO
        )
        Spacer()
        Image(
            modifier = Modifier.size(30.px).onClick { onBagClicked() },
            src = Res.Icons.SHOPPING_BAG
        )
    }
}