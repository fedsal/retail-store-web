package org.fedsal.buenpuerto

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.fedsal.buenpuerto.composables.LeftPanel
import org.fedsal.buenpuerto.composables.OrderDetail
import org.fedsal.buenpuerto.data.datasource.OrderLocalDataSource
import org.fedsal.buenpuerto.data.repository.OrderRepository
import org.fedsal.buenpuerto.domain.model.Order
import org.fedsal.buenpuerto.pages.OrdersPage
import org.fedsal.buenpuerto.viewmodel.ordermanager.OrderManagerViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val viewModel = remember {
        OrderManagerViewModel(
            OrderRepository(
                object : OrderLocalDataSource {
                    override suspend fun saveOrder(order: Order) {
                        TODO("Not yet implemented")
                    }

                    override suspend fun getOrder(): Order {
                        TODO("Not yet implemented")
                    }

                    override suspend fun updateOrder(order: Order) {
                        TODO("Not yet implemented")
                    }

                }
            )
        )
    }

    MaterialTheme {
        Surface(modifier = Modifier.background(Color.Black).fillMaxSize(), color = Color.Black) {
            Row(modifier = Modifier.fillMaxSize()) {
                LeftPanel()
                AppContent(viewModel)
            }
        }
    }
}


@Composable
fun AppContent(viewModel: OrderManagerViewModel) {
    val uiState = viewModel.uiState.collectAsState()
    var focusedOrder by remember { mutableStateOf<Order?>(null) }

    Box(
        Modifier.background(color = Color.Black).padding(top = 60.dp).fillMaxSize().fillMaxSize()
            .clip(RoundedCornerShape(topStart = 20.dp))
            .background(Color.White), contentAlignment = Alignment.Center
    ) {
        if (uiState.value.isLoading) {
            CircularProgressIndicator(color = Color.Black)
        } else {
            OrdersPage(
                orders = uiState.value.orders,
                onClickItem = { order ->
                    focusedOrder = order
                }
            )
        }
        AnimatedVisibility(
            visible = focusedOrder != null,
            enter = slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth }, // desde la derecha
                animationSpec = tween(durationMillis = 300)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth }, // hacia la derecha
                animationSpec = tween(durationMillis = 300)
            ),
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            focusedOrder?.let {
                OrderDetail(
                    order = it,
                    onDismiss = {
                        focusedOrder = null
                    }
                )
            }
        }
    }

}
