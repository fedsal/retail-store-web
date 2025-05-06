package org.fedsal.buenpuerto.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.rounded.KeyboardDoubleArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.fedsal.buenpuerto.domain.model.Order
import org.fedsal.buenpuerto.domain.model.OrderItem
import org.fedsal.buenpuerto.utils.formatDecimal
import org.jetbrains.compose.resources.painterResource
import tienda_buen_puerto.composeapp.generated.resources.Res
import tienda_buen_puerto.composeapp.generated.resources.product_placeholder

@Composable
fun OrderDetail(
    order: Order,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .clip(RoundedCornerShape(topStart = 20.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(topStart = 20.dp))
            .fillMaxWidth(.5f)
            .fillMaxHeight()
            .pointerInput(Unit) { }
            .padding(20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        Icon(
            imageVector = Icons.Rounded.KeyboardDoubleArrowRight,
            contentDescription = "Cerrar",
            modifier = Modifier.clickable { onDismiss() }.size(24.dp)
        )
        Column(Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(Modifier.fillMaxWidth()) {
                Text("Cliente: ${order.clientName}", color = Color.Black)
                Spacer(modifier = Modifier.weight(1f))
                Text("ID del pedido: ${order.id}", color = Color.Black)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider()
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(Modifier.weight(1f)) {
                items(order.products) { orderItem ->
                    OrderItemCard(orderItem)
                    Divider()
                }
            }
            Divider()
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Total: $ ${order.total.formatDecimal()}",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }
}


@Composable
fun OrderItemCard(orderItem: OrderItem) {
    val clipboardManager = LocalClipboardManager.current

    Row(
        modifier = Modifier.padding(vertical = 10.dp).height(100.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = orderItem.product.imagesUrl.first(),
            placeholder = painterResource(Res.drawable.product_placeholder),
            error = painterResource(Res.drawable.product_placeholder),
            contentDescription = null,
            modifier = Modifier
                .padding(10.dp)
                .size(70.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp))
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            Row(Modifier.fillMaxWidth().weight(1f)) {
                Text(
                    orderItem.product.name,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.width(20.dp))
                Text("Unidades: ${orderItem.quantity}")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Código: ${orderItem.product.code}")
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copiar código",
                        modifier = Modifier
                            .size(22.dp)
                            .clickable {
                                clipboardManager.setText(AnnotatedString(orderItem.product.code))
                            }
                            .padding(start = 6.dp),
                        tint = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text("Precio: $ ${orderItem.product.price.formatDecimal()}")
            }
        }
    }
}
