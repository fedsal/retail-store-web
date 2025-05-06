package org.fedsal.buenpuerto.sections

import androidx.compose.runtime.Composable
import com.stevdza.san.kotlinbs.components.BSCarousel
import com.stevdza.san.kotlinbs.models.CarouselItem
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.margin
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun ImageCarouselSection(
    imageList: List<String>,
) {
    BSCarousel(
        modifier = Modifier.margin(all = 0.px),
        items = imageList.map {
            CarouselItem(
                image = it
            )
        },
        width = 100.percent,
        height = 300.px,
        objectFit = ObjectFit.Cover,
        dark = true
    )
}