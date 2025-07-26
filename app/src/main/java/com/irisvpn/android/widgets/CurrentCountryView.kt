package com.irisvpn.android.widgets

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.irisvpn.android.R
import com.irisvpn.android.appConfig.theme.AppHorizontalMargin
import com.irisvpn.android.appConfig.theme.AppTintEffectColor
import com.irisvpn.android.appConfig.theme.BorderSize
import com.irisvpn.android.appConfig.theme.IconHugeSize
import com.irisvpn.android.appConfig.theme.IconNormalSize
import com.irisvpn.android.appConfig.theme.MediumText
import com.irisvpn.android.appConfig.theme.PrimaryTextColor
import com.irisvpn.android.appConfig.theme.RoundCornerSize
import com.irisvpn.android.appConfig.theme.SecondaryTextColor
import com.irisvpn.android.appConfig.theme.SmallText
import com.irisvpn.android.appConfig.theme.SpaceL
import com.irisvpn.android.appConfig.theme.SpaceM
import com.irisvpn.android.appConfig.theme.SpaceS
import com.irisvpn.android.domain.core.CurrentServerState

@Composable
fun CurrentCountryView(
    currentServer: CurrentServerState,
    onRetryClick: () -> Unit,
    onBoxCLick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = AppHorizontalMargin)
            .fillMaxWidth()
            .background(Color.AppTintEffectColor)
            .border(
                width = BorderSize, color = Color.White, shape = RoundedCornerShape(RoundCornerSize)
            )
            .clickable(onClick = onBoxCLick)

    ) {
        Row(modifier = Modifier.padding(SpaceS), verticalAlignment = Alignment.CenterVertically) {
            when (currentServer) {
                is CurrentServerState.Loading -> {
                    LoadingCurrentServer()
                }

                is CurrentServerState.OnError -> {
                    ErrorCurrentServer(currentServer, onRetryClick)
                }

                is CurrentServerState.OnReady -> {
                    SuccessLoadedCurrentServer(currentServer)
                }
            }
        }
    }
}

@Composable
private fun RowScope.SuccessLoadedCurrentServer(state: CurrentServerState.OnReady) {
    AsyncImage(
        model = state.current.flag,
        contentScale = ContentScale.Crop,
        contentDescription = "test",
        modifier = Modifier
            .size(IconHugeSize)
            .clip(CircleShape)
    )
    Spacer(modifier = Modifier.padding(start = SpaceM))
    Column {
        Text(
            text = state.current.name ?: "Server",
            color = Color.PrimaryTextColor,
            style = MediumText
        )
        Spacer(modifier = Modifier.padding(top = SpaceS))
        IrisTagView(state.current.tags?:"")

    }
    Spacer(modifier = Modifier.weight(1f))
    Icon(
        imageVector = ImageVector.vectorResource(R.drawable.rightarrow),
        contentDescription = "test",
        tint = Color.PrimaryTextColor,
        modifier = Modifier.size(IconNormalSize)
    )
}

@Composable
private fun LoadingCurrentServer() {
    Box(
        modifier = Modifier
            .size(IconHugeSize)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.Gray.copy(alpha = 0.3f))
            .shimmerEffect()
    )

    Spacer(modifier = Modifier.padding(start = SpaceM))

    Column {
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(SpaceL)
                .clip(RoundedCornerShape(SpaceS))
                .background(Color.Gray.copy(alpha = 0.3f))
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.padding(top = SpaceS))
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(SpaceM)
                .clip(RoundedCornerShape(SpaceS))
                .background(Color.Gray.copy(alpha = 0.2f))
                .shimmerEffect()
        )
    }
}

@Composable
private fun RowScope.ErrorCurrentServer(
    state: CurrentServerState.OnError,
    onRetryClick: () -> Unit
) {

    Icon(
        imageVector = Icons.Default.Info,
        contentDescription = "Connection error",
        tint = Color.Red.copy(alpha = 0.5f),
        modifier = Modifier.size(IconHugeSize)
    )

    Spacer(modifier = Modifier.padding(start = SpaceM))

    Column {
        Text(
            text = "Error loading servers!", color = Color.PrimaryTextColor, style = MediumText
        )
        Spacer(modifier = Modifier.padding(top = SpaceS))
        Text(
            text = state.message,
            color = Color.SecondaryTextColor,
            style = SmallText,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

    Spacer(modifier = Modifier.weight(1f))

    Icon(imageVector = Icons.Default.Refresh,
        contentDescription = "Retry connection",
        tint = Color.PrimaryTextColor.copy(alpha = 0.6f),
        modifier = Modifier
            .size(IconNormalSize)
            .clickable {
                onRetryClick.invoke()
            })
}

@Composable
fun Modifier.shimmerEffect(): Modifier = composed {
    var size = remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "shimmer")
    val startOffsetX = transition.animateFloat(
        initialValue = -2 * size.value.width.toFloat(),
        targetValue = 2 * size.value.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1300),
            initialStartOffset = StartOffset(300),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color.Transparent, Color.White.copy(alpha = 0.4f), Color.Transparent
            ), start = Offset(startOffsetX.value, 0f), end = Offset(
                startOffsetX.value + size.value.width.toFloat(), size.value.height.toFloat()
            )
        )
    ).onGloballyPositioned { size.value = it.size }
}


