package org.fedsal.buenpuerto.pages

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.fedsal.buenpuerto.composables.OrdersList
import org.fedsal.buenpuerto.domain.model.Order

@Composable
fun OrdersPage(orders: List<Order>, onClickItem: (Order) -> Unit) {
    var query by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
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
                value = query,
                onValueChange = { query = it },
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
                ),
                singleLine = true
            )
        }
        OrdersList(
            orders = orders,
            onClickItem = onClickItem
        )
    }
}
