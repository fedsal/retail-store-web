package org.fedsal.buenpuerto

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import org.fedsal.buenpuerto.composables.LeftPanel
import org.fedsal.buenpuerto.data.datasource.OrderLocalDataSource
import org.fedsal.buenpuerto.data.repository.OrderRepository
import org.fedsal.buenpuerto.domain.model.Order
import org.fedsal.buenpuerto.utils.formatDecimal
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

    Column(
        modifier = Modifier.background(color = Color.Black).padding(top = 60.dp).fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 20.dp))
                .background(Color.White)
                .padding(20.dp)
                .fillMaxSize()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Pedidos",
                    modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 16.dp),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                TextField(
                    value = "",
                    onValueChange = { },
                    placeholder = {
                        Text(text = "Buscar")
                    },
                    modifier = Modifier
                        .fillMaxWidth(.4f)
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            color = Color.LightGray,
                            width = 1.dp,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
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
        Divider()

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
                SelectionContainer {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    ) {
                        Text(order.id, modifier = Modifier.weight(1f))
                        Text(order.clientName, modifier = Modifier.weight(2f))
                        Text("$ ${order.total.formatDecimal()}", modifier = Modifier.weight(1f))
                        Text(order.products.size.toString(), modifier = Modifier.weight(1f))
                    }
                }
                Divider()
            }
        }
    }
}
