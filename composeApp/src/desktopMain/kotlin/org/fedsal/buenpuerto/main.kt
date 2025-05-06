package org.fedsal.buenpuerto

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import org.jetbrains.compose.resources.painterResource
import tienda_buen_puerto.composeapp.generated.resources.Res
import tienda_buen_puerto.composeapp.generated.resources.logo

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Tienda Buen Puerto",
        state = WindowState(
            size = DpSize(1280.dp, 720.dp),
            isMinimized = false
        ),
        icon = painterResource(Res.drawable.logo),
    ) {
        App()
    }
}