package com.badger.familyorgfe.features.appjourney.products.fridge.fridgeitem

import android.annotation.SuppressLint
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.badger.familyorgfe.R
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.ext.hasFractionalPart
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme
import kotlin.math.roundToInt

@Composable
fun FridgeListItem(
    item: FridgeItem,
    isExpanded: Boolean,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    onEdit: (FridgeItem) -> Unit,
    onDelete: (FridgeItem) -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        ActionIconsLayout(
            onEdit = { onEdit(item) },
            onDelete = { onDelete(item) }
        )

        SwipeableContent(
            item = item,
            isExpanded = isExpanded,
            onExpand = onExpand,
            onCollapse = onCollapse
        )
    }
}

/**
 *
 * */

@Composable
private fun ActionIconsLayout(
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        ActionIcon(onClick = onEdit, size = 28.dp, iconResourceId = R.drawable.ic_edit_pencil)

        Spacer(modifier = Modifier.width(8.dp))
        ActionIcon(onClick = onDelete, size = 32.dp, iconResourceId = R.drawable.ic_close)
    }
}

@Composable
private fun ActionIcon(
    onClick: () -> Unit,
    size: Dp,
    iconResourceId: Int
) {
    Box(
        modifier = Modifier.size(48.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(size = size)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        bounded = false,
                        color = FamilyOrganizerTheme.colors.primary
                    ),
                    onClick = onClick
                ),
            painter = painterResource(id = iconResourceId),
            contentDescription = null,
            tint = FamilyOrganizerTheme.colors.darkClay
        )
    }
}

/**
 *
 * */


private const val ANIMATION_DURATION = 500
private val CARD_OFFSET_VALUE_DP = (-115).dp

private const val ITEM_TRANSITION_LABEL = "itemTransition"
private const val ITEM_OFFSET_TRANSITION = "itemOffsetTransition"

private const val MINIMUM_DRAG_AMOUNT = 10

@Composable
@SuppressLint("UnusedTransitionTargetStateParameter")
private fun SwipeableContent(
    item: FridgeItem,
    isExpanded: Boolean,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
) {

    val cardOffsetPxValue = with(LocalDensity.current) { CARD_OFFSET_VALUE_DP.toPx() }
    val transitionState = remember { MutableTransitionState(isExpanded) }

    val transition = updateTransition(
        transitionState = transitionState,
        label = ITEM_TRANSITION_LABEL
    )
    val offsetTransition by transition.animateFloat(
        label = ITEM_OFFSET_TRANSITION,
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = {
            if (isExpanded) {
                cardOffsetPxValue
            } else {
                0f
            }
        }
    )

    val currentOffset = IntOffset((offsetTransition).roundToInt(), 0)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 16.dp, end = 16.dp)
            .offset { currentOffset }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    when {
                        dragAmount > 0 -> onCollapse()
                        dragAmount <= MINIMUM_DRAG_AMOUNT -> onExpand()
                        else -> Unit
                    }
                }
            },
        shape = RoundedCornerShape(16.dp),
        elevation = 3.dp,
        content = { ProductListItemCardContent(item) }
    )
}

@Composable
private fun ProductListItemCardContent(
    item: FridgeItem
) {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 14.dp)
        ) {

            Text(
                text = item.name,
                style = FamilyOrganizerTheme.textStyle
                    .subtitle2.copy(fontWeight = FontWeight.Medium),
                color = FamilyOrganizerTheme.colors.blackPrimary,
                maxLines = 1
            )

            if (item.hasMeasureAndQuantity) {
                Row {
                    val quantity = item.quantity?.takeIf(Double::hasFractionalPart)
                        ?: item.quantity?.toInt()

                    Text(
                        text = quantity.toString(),
                        style = FamilyOrganizerTheme.textStyle.body,
                        color = FamilyOrganizerTheme.colors.blackPrimary,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = getMeasureString(item.quantity!!, item.measure!!),
                        style = FamilyOrganizerTheme.textStyle
                            .body.copy(fontWeight = FontWeight.Light),
                        color = FamilyOrganizerTheme.colors.blackPrimary,
                        maxLines = 1
                    )
                }
            } else {
                Text(
                    text = stringResource(id = R.string.fridge_quantity_unknown),
                    style = FamilyOrganizerTheme.textStyle
                        .body.copy(fontWeight = FontWeight.Light),
                    color = FamilyOrganizerTheme.colors.blackPrimary,
                    maxLines = 1
                )
            }
        }

        if (item.hasExpiration) {
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(0.5.dp),
                color = FamilyOrganizerTheme.colors.lightGray
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(64.dp)
                    .align(Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(2.dp))

                val (expLeft, expType) = getExpirationPair(item.expDaysLeft ?: 0)
                Text(
                    text = expLeft.toString(),
                    style = FamilyOrganizerTheme.textStyle
                        .headline3.copy(fontWeight = FontWeight.SemiBold),
                    color = item.expStatus?.color ?: Color.Black,
                    maxLines = 1
                )

                Text(
                    text = getExpirationString(
                        quantityLeft = expLeft,
                        expirationType = expType
                    ),
                    style = FamilyOrganizerTheme.textStyle
                        .body.copy(fontWeight = FontWeight.Light),
                    color = FamilyOrganizerTheme.colors.blackPrimary,
                    maxLines = 1
                )
            }

        }
    }
}

/**
 *
 * */

private const val THOUSAND = 1000

@Composable
fun getMeasureString(
    quantity: Double,
    measure: Product.Measure
): String {
    val measureQualifier = (quantity * THOUSAND).roundToInt()
    val pluralsId = when (measure) {
        Product.Measure.LITER -> R.plurals.fridge_measure_liter_plurals
        Product.Measure.KILOGRAM -> R.plurals.fridge_measure_kilogram_plurals
        Product.Measure.THINGS -> R.plurals.fridge_measure_things_plurals
    }

    return LocalContext.current.resources.getQuantityString(pluralsId, measureQualifier)
}

/**
 *
 * */

@Composable
private fun getExpirationString(
    quantityLeft: Int,
    expirationType: ExpirationType
): String {
    val pluralsId = when (expirationType) {
        ExpirationType.DAY -> R.plurals.fridge_expiration_day_plurals
        ExpirationType.MONTH -> R.plurals.fridge_expiration_month_plurals
        ExpirationType.YEAR -> R.plurals.fridge_expiration_year_plurals
    }

    return LocalContext.current.resources.getQuantityString(pluralsId, quantityLeft)
}

/**
 *
 * */

private enum class ExpirationType(val days: Int) {
    DAY(days = 1),
    MONTH(days = 31),
    YEAR(days = 365)
}

@Composable
private fun getExpirationPair(
    daysLeft: Int
): Pair<Int, ExpirationType> {
    val expirationType = when {
        daysLeft in ExpirationType.MONTH.days..ExpirationType.YEAR.days -> ExpirationType.MONTH
        daysLeft > ExpirationType.YEAR.days -> ExpirationType.YEAR
        else -> ExpirationType.DAY
    }

    return daysLeft / expirationType.days to expirationType
}