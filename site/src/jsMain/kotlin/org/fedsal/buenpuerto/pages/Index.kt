package org.fedsal.buenpuerto.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.stevdza.san.kotlinbs.components.BSSpinner
import com.stevdza.san.kotlinbs.forms.BSInput
import com.stevdza.san.kotlinbs.models.InputSize
import com.stevdza.san.kotlinbs.models.SpinnerStyle
import com.stevdza.san.kotlinbs.models.SpinnerVariant
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.icons.fa.FaXmark
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
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
import org.fedsal.buenpuerto.utils.FONT_FAMILY
import org.fedsal.buenpuerto.utils.Res
import org.fedsal.buenpuerto.viewmodel.checkout.OrderViewModel
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button

@Page("{productCode}")
@Composable
fun HomePage() {
    val viewModel = OrderViewModel(
        OrderRepository(
            localDataSource = OrderLocalDataSourceImpl(),
            remoteDataSource = SupabaseOrderRemoteDataSource()
        )
    )

    val ctx = rememberPageContext()
    val productCode = ctx.route.params.getValue(Res.QueryParams.PRODUCT_CODE)

    LaunchedEffect(productCode) {
        viewModel.initializeData(productCode)
    }

    val uiState = viewModel.uiState.collectAsState()

    var menuOpened by remember { mutableStateOf(false) }
    var orderPlaced by remember { mutableStateOf(false) }

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
                HeaderSection(onBagClicked = { menuOpened = true })
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
                        viewModel.sendOrder("Federico")
                    }
                )
            }
            if (orderPlaced) {
                Box(Modifier.fillMaxSize().background(Colors.Black.copy(alpha = 150)).zIndex(3)) {
                    OrderPlacementDialog(
                        modifier = Modifier.align(Alignment.TopCenter).margin { top(60.px) },
                        onContinue = {
                            viewModel.sendOrder(it)
                        }
                    ) {
                        orderPlaced = false
                    }
                }
            }
        }
    }
}

@Composable
fun OrderPlacementDialog(
    modifier: Modifier = Modifier,
    onContinue: (clientName: String) -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.background(Colors.White).fillMaxHeight(30.percent).width(90.percent)
            .borderRadius(10.px).padding(30.px)
    ) {
        Row(Modifier.fillMaxWidth().margin { bottom(20.px) }, verticalAlignment = Alignment.CenterVertically) {
            FaXmark(modifier = Modifier.cursor(Cursor.Pointer).margin(right = 20.px).onClick {
                onDismiss()
            }.color(Colors.Black), size = IconSize.XL)
            SpanText("Comprar carrito", modifier = Modifier.fillMaxWidth().textAlign(TextAlign.Start).fontWeight(
                FontWeight.Bold).color(Colors.Black).fontSize(20.px) )
        }
        var inputValue by remember { mutableStateOf("") }
        BSInput(
            modifier = Modifier.fillMaxWidth().height(60.px).margin(bottom = 30.px),
            size = InputSize.Large,
            value = inputValue,
            placeholder = "Escriba su nombre",
            onValueChange = {
                inputValue = it
            }
        )
        Button(
            attrs = Modifier
                .height(50.px)
                .border(width = 0.px)
                .borderRadius(r = 10.px)
                .backgroundColor(Colors.Black)
                .color(Colors.White)
                .cursor(Cursor.Pointer)
                .fillMaxWidth()
                .onClick { onContinue(inputValue) }
                .toAttrs()
        ) {
            SpanText(
                modifier = Modifier
                    .fillMaxWidth()
                    .fontFamily(FONT_FAMILY)
                    .fontSize(16.px)
                    .fontWeight(FontWeight.Bold)
                    .color(Colors.White),
                text = "Continuar".uppercase()
            )
        }
    }
}




