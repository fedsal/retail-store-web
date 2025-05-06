package org.fedsal.buenpuerto.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.fedsal.buenpuerto.domain.model.Order
import org.fedsal.buenpuerto.utils.formatDecimal

@Composable
fun OrdersList(orders: List<Order>, onClickItem: (Order) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Header
        Divider()

        Row(modifier = Modifier.fillMaxWidth().padding(all = 12.dp)) {
            Text("Id", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
            Text("Cliente", modifier = Modifier.weight(2f), fontWeight = FontWeight.Bold)
            Text("Total", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
            Text("Productos", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
        }

        Divider()

        // List
        LazyColumn {
            items(orders) { order ->
                SelectionContainer(modifier = Modifier.clickable { onClickItem(order) }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 12.dp)
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