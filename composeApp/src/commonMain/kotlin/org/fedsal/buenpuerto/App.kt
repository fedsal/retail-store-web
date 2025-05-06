package org.fedsal.buenpuerto

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.fedsal.buenpuerto.data.repository.OrderRepository
import org.fedsal.buenpuerto.viewmodel.checkout.OrderViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val viewModel = remember { OrderViewModel(
        OrderRepository(

        )
    ) }

    MaterialTheme {

    }
}