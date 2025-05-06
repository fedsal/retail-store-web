package org.fedsal.buenpuerto.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import tienda_buen_puerto.composeapp.generated.resources.Res
import tienda_buen_puerto.composeapp.generated.resources.logo

@Composable
fun LeftPanel() {
    Column(
        modifier = Modifier.fillMaxWidth(.2f).fillMaxHeight().background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Navigation Content
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(Res.drawable.logo),
            contentDescription = "Logo",
        )

        Spacer(modifier = Modifier.height(60.dp))

        Column(
            modifier = Modifier.padding(20.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            NavigationItem(Icons.Filled.Home, "Pedidos") {}
            Spacer(modifier = Modifier.height(20.dp))
            NavigationItem(Icons.Outlined.FavoriteBorder, "Productos") {}
        }
    }
}