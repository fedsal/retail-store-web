package org.fedsal.buenpuerto.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.stevdza.san.kotlinbs.components.BSSpinner
import com.stevdza.san.kotlinbs.models.SpinnerStyle
import com.stevdza.san.kotlinbs.models.SpinnerVariant
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.coroutines.delay
import org.fedsal.buenpuerto.components.OrderConfirmed
import org.fedsal.buenpuerto.components.OrderPlacementDialog
import org.fedsal.buenpuerto.data.OrderLocalDataSourceImpl
import org.fedsal.buenpuerto.data.datasource.supabase.SupabaseOrderRemoteDataSource
import org.fedsal.buenpuerto.data.repository.OrderRepository
import org.fedsal.buenpuerto.domain.model.OrderItem
import org.fedsal.buenpuerto.sections.CheckoutSection
import org.fedsal.buenpuerto.sections.FooterSection
import org.fedsal.buenpuerto.sections.HeaderSection
import org.fedsal.buenpuerto.sections.ImageCarouselSection
import org.fedsal.buenpuerto.sections.ProductPriceSection
import org.fedsal.buenpuerto.sections.ProductTitleSection
import org.fedsal.buenpuerto.utils.Res
import org.fedsal.buenpuerto.viewmodel.checkout.OrderViewModel
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Page
@Composable
fun HomePage() {
    val viewModel = remember {
        OrderViewModel(
            OrderRepository(
                localDataSource = OrderLocalDataSourceImpl(),
                remoteDataSource = SupabaseOrderRemoteDataSource()
            )
        )
    }

    val ctx = rememberPageContext()
    val productCode = ctx.route.params[Res.QueryParams.PRODUCT_CODE]

    LaunchedEffect(productCode) {
        viewModel.initializeData(productCode ?: "")
    }

    val uiState = viewModel.uiState.collectAsState()

    var menuOpened by remember { mutableStateOf(false) }
    var orderPlaced by remember { mutableStateOf(false) }
    var itemAdded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.value.isLoading) {
            BSSpinner(
                modifier = Modifier.align(Alignment.Center),
                variant = SpinnerVariant.Large,
                style = SpinnerStyle.Dark
            )
        } else {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                HeaderSection(
                    onBagClicked = { menuOpened = true },
                    itemCount = uiState.value.order.products.size
                )
                ImageCarouselSection(uiState.value.product.imagesUrl)
                ProductTitleSection(uiState.value.product.name)
                ProductPriceSection(uiState.value.product.price)
                Spacer()
                FooterSection { quantity ->
                    val orderItem = OrderItem(
                        product = uiState.value.product,
                        quantity = quantity
                    )
                    viewModel.addItem(orderItem)
                    itemAdded = true
                }
            }
            if (menuOpened) {
                CheckoutSection(
                    onMenuClosed = { menuOpened = false }, order = uiState.value.order,
                    onDecrement = { viewModel.removeItem(it.copy(quantity = 1)) },
                    onIncrement = { viewModel.addItem(it.copy(quantity = 1)) },
                    onRemove = { viewModel.deleteItem(it) },
                    onPlaceOrder = {
                        orderPlaced = true
                    }
                )
            }
            if (orderPlaced) {
                Box(Modifier.fillMaxSize().background(Colors.Black.copy(alpha = 150)).zIndex(3)) {
                    OrderPlacementDialog(
                        modifier = Modifier.align(Alignment.TopCenter).margin { top(60.px) },
                        onContinue = {
                            console.log("Order enviada al mostrador: $it")
                            viewModel.sendOrder(it)
                        }
                    ) {
                        orderPlaced = false
                    }
                }
            }
            if (uiState.value.isOrderConfirmed) {
                OrderConfirmed(
                    modifier = Modifier.fillMaxSize(),
                    orderId = uiState.value.order.id,
                    onDismiss = {
                        viewModel.clearOrder()
                        menuOpened = false
                        orderPlaced = false
                    }
                )
            }
            if (itemAdded) {
                LaunchedEffect(itemAdded) {
                    delay(2000)
                    itemAdded = false
                }
                ItemAddedPopUp(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .margin(bottom = 70.px),
                    onGoToCart = { menuOpened = true }
                )
            }
        }
    }
}

@Composable
fun ItemAddedPopUp(modifier: Modifier = Modifier, onGoToCart: () -> Unit = {}) {
    Row(
        modifier = modifier
            .borderRadius(16.px)
            .height(60.px)
            .fillMaxWidth(95.percent)
            .background(Colors.Black)
            .padding(20.px),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SpanText(
            "Producto agregado",
            modifier = Modifier
                .color(Colors.White)
                .fontSize(16.px)
        )
        SpanText(
            "Ver carrito",
            modifier = Modifier
                .color(Colors.White)
                .textDecorationLine(TextDecorationLine.Underline)
                .fontWeight(FontWeight.SemiBold)
                .fontSize(16.px)
                .onClick { onGoToCart() }
        )
    }
}