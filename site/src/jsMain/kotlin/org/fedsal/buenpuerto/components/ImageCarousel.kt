package org.fedsal.buenpuerto.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.objectFit
import com.varabyte.kobweb.silk.components.graphics.Image
import org.jetbrains.compose.web.css.px

@Composable
fun ImageCarousel() {
    Image(
        src = "https://http2.mlstatic.com/D_NQ_NP_2X_661732-MLA82942925465_032025-F.webp",
        alt = "Product Image 1",
        modifier = Modifier.fillMaxWidth().height(300.px).objectFit(ObjectFit.ScaleDown),
    )
}