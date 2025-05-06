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
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
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
                CheckoutSection(onMenuClosed = { menuOpened = false }, order = uiState.value.order,
                    onDecrement = { viewModel.removeItem(it) },
                    onIncrement = { viewModel.addItem(it) },
                    onRemove = { viewModel.deleteItem(it) },
                    onPlaceOrder = {

                        viewModel.sendOrder("Federico")
                    }
                )
            }
        }
    }
}



