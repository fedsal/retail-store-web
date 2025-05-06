package org.fedsal.buenpuerto.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardDoubleArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.fedsal.buenpuerto.domain.model.Order

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
            .padding(20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        Icon(
            imageVector = Icons.Rounded.KeyboardDoubleArrowRight,
            contentDescription = "Cerrar",
            modifier = Modifier.clickable { onDismiss() }.size(24.dp)
        )
    }
}