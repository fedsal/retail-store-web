package org.fedsal.buenpuerto.components

import androidx.compose.runtime.Composable
import com.stevdza.san.kotlinbs.components.BSCarousel
import com.stevdza.san.kotlinbs.models.CarouselItem
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.margin
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun ImageCarousel() {
    BSCarousel(
        modifier = Modifier.margin(all = 0.px),
        items = listOf(
            CarouselItem(
                image = "https://http2.mlstatic.com/D_NQ_NP_2X_661732-MLA82942925465_032025-F.webp",
                title = "Moraine Lake"
            ),
            CarouselItem(
                image = "https://images.pexels.com/photos/147411/italy-mountains-dawn-daybreak-147411.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                title = "Italy"
            ),
            CarouselItem(
                image = "https://images.pexels.com/photos/1166209/pexels-photo-1166209.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                title = "Lavender"
            ),
        ),
        width = 100.percent,
        height = 300.px,
        objectFit = ObjectFit.ScaleDown,
        dark = true
    )
}