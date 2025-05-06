package org.fedsal.buenpuerto

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.fedsal.buenpuerto.data.datasource.OrderLocalDataSource
import org.fedsal.buenpuerto.data.repository.OrderRepository
import org.fedsal.buenpuerto.domain.model.Order
import org.fedsal.buenpuerto.viewmodel.ordermanager.OrderManagerViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tienda_buen_puerto.composeapp.generated.resources.Res
import tienda_buen_puerto.composeapp.generated.resources.logo

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
fun LeftPanel() {
    Column(
        modifier = Modifier.fillMaxWidth(.2f).fillMaxHeight().background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Navigation Content
        Image(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            painter = painterResource(Res.drawable.logo),
            contentDescription = "Logo",
        )
    }
}

@Composable
fun AppContent(viewModel: OrderManagerViewModel) {
    val uiState = viewModel.uiState.collectAsState()

    Column(modifier = Modifier.background(color = Color.Black).padding(top = 60.dp).fillMaxSize()) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 20.dp))
                .background(Color.White)
                .padding(20.dp)
                .fillMaxSize()
        ) {
            Text(
                "Pedidos",
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            OrdersList(
                orders = uiState.value.orders
            )
        }
    }
}

@Composable
fun OrdersList(orders: List<Order>) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Header
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
            Text("Id", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
            Text("Cliente", modifier = Modifier.weight(2f), fontWeight = FontWeight.Bold)
            Text("Total", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
            Text("Productos", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
        }

        Divider()

        // List
        LazyColumn {
            items(orders) { order ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    Text(order.id, modifier = Modifier.weight(1f))
                    Text(order.clientName, modifier = Modifier.weight(2f))
                    Text("$ ${order.total}", modifier = Modifier.weight(1f))
                    Text(order.products.size.toString(), modifier = Modifier.weight(1f))
                }
                Divider()
            }
        }
    }
}
