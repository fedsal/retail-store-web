package org.fedsal.buenpuerto.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import org.fedsal.buenpuerto.components.FooterSection
import org.fedsal.buenpuerto.components.Header
import org.fedsal.buenpuerto.components.ImageCarousel
import org.fedsal.buenpuerto.components.ProductPrice
import org.fedsal.buenpuerto.components.ProductTitle

@Page
@Composable
fun HomePage() {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Header()
        ImageCarousel()
        ProductTitle()
        ProductPrice()
        Spacer()
        FooterSection()
    }
}



