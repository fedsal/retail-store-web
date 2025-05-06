package org.fedsal.buenpuerto

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "tienda-buen-puerto",
    ) {
        App()
    }
}