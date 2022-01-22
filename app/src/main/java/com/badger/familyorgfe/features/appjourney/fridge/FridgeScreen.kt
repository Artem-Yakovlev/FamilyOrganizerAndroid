package com.badger.familyorgfe.features.appjourney.fridge

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.badger.familyorgfe.R
import com.badger.familyorgfe.ui.elements.BaseToolbar
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme
import org.threeten.bp.LocalDateTime

@Composable
fun FridgeScreen(
    modifier: Modifier,
    viewModel: IFridgeViewModel = hiltViewModel<FridgeViewModel>()
) {

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Toolbar()

        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            items(10) {
                ProductItem(item = ProductData.mock())

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun Toolbar() {
    BaseToolbar {
        Spacer(modifier = Modifier.width(16.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.fridge_toolbar_title),
            style = FamilyOrganizerTheme.textStyle.headline2,
            color = FamilyOrganizerTheme.colors.blackPrimary
        )

        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { },
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            tint = FamilyOrganizerTheme.colors.blackPrimary
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { },
            painter = painterResource(id = R.drawable.ic_more_vert),
            contentDescription = null,
            tint = FamilyOrganizerTheme.colors.blackPrimary
        )

        Spacer(modifier = Modifier.width(16.dp))
    }
}


data class ProductData(
    val id: String,
    val name: String,
    val quantity: Double?,
    val measure: Measure?,
    val category: Category,
    val expiryDate: LocalDateTime?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun mock() = ProductData(
            id = "123",
            name = "Хлеб",
            quantity = 0.5,
            measure = Measure.THINGS,
            category = Category.HEALTHY,
            expiryDate = LocalDateTime.now().plusDays(3),
            createdAt = LocalDateTime.now().minusDays(3),
            updatedAt = LocalDateTime.now().minusDays(3)
        )
    }
}

enum class Measure {
    LITER,
    KILOGRAM,
    THINGS
}

enum class Category {
    JUNK,
    HEALTHY,
    DEFAULT
}

@Composable
private fun ProductItem(item: ProductData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 3.dp
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {

        }
    }
}