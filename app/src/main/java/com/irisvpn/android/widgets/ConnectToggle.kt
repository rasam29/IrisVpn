package com.irisvpn.android.widgets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.irisvpn.android.R
import com.irisvpn.android.appConfig.theme.AppTintEffectColor
import com.irisvpn.android.appConfig.theme.ConnectTextColor
import com.irisvpn.android.appConfig.theme.IconMediumSize
import com.irisvpn.android.appConfig.theme.IconSmallSize
import com.irisvpn.android.appConfig.theme.SpaceXL
import com.irisvpn.android.appConfig.theme.SpaceXS
import com.irisvpn.android.domain.core.GeneralState

@Composable
fun ConnectToggleView(connectionState: GeneralState, onConnectClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = 200.dp, topEnd = 200.dp
                )
            )
            .fillMaxWidth()
            .background(Color.AppTintEffectColor)
            .height(450.dp),
        contentAlignment = Alignment.Center
    ) {
        ConnectButton(onConnectClick, state = connectionState)
    }
}

@Composable
private fun ConnectButton(
    onClick: () -> Unit, state: GeneralState
) {
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(190.dp)
                .background(Color(0xFF1B1E3C), shape = CircleShape)
                .shadow(20.dp, shape = CircleShape, ambientColor = Color.AppTintEffectColor)
                .clickable { onClick() }) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .background(Color(0xFF2A2E4F), shape = CircleShape)
            )

            Box(
                modifier = Modifier
                    .size(130.dp)
                    .background(Color(0xFF313659), shape = CircleShape)
            )

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.power),
                contentDescription = "Power Button",
                tint = Color.White,
                modifier = Modifier.size(IconMediumSize)
            )
        }

        Spacer(modifier = Modifier.height(SpaceXL))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(state.findIconByState()),
                contentDescription = null,
                tint = state.findColorByState(),
                modifier = Modifier.size(IconSmallSize)
            )
            Spacer(modifier = Modifier.width(SpaceXS))
            Text(
                text = state.findLabelByState(),
                color = state.findColorByState()
            )
        }
    }
}

@DrawableRes
private fun GeneralState.findIconByState(): Int = when (this) {
    is GeneralState.Connected -> R.drawable.resource_protected
    GeneralState.Connecting,
    GeneralState.DisConnected,
    GeneralState.DisConnectedOfAdShowFailed,
    GeneralState.DisConnectedOfDismiss,
    GeneralState.WaitForServerListToLoad,
    GeneralState.MustSeeAdToContinue -> R.drawable.unprotected
    is GeneralState.SessionFetchError -> R.drawable.unprotected
}

@Composable
private fun GeneralState.findLabelByState(): String {
    val resId = when (this) {
        is GeneralState.Connected -> R.string.connected
        GeneralState.Connecting -> R.string.connecting
        GeneralState.DisConnected,
        GeneralState.DisConnectedOfAdShowFailed,
        GeneralState.DisConnectedOfDismiss,
        GeneralState.WaitForServerListToLoad,
        GeneralState.MustSeeAdToContinue -> R.string.not_connected

        is GeneralState.SessionFetchError -> R.string.not_connected
    }
    return stringResource(resId)
}

fun GeneralState.findColorByState(): Color = when (this) {
    is GeneralState.Connected -> Color.Green
    GeneralState.Connecting -> Color.ConnectTextColor
    GeneralState.DisConnected,
    GeneralState.DisConnectedOfAdShowFailed,
    GeneralState.DisConnectedOfDismiss,
    GeneralState.WaitForServerListToLoad,
    GeneralState.MustSeeAdToContinue -> Color.Red
    is GeneralState.SessionFetchError -> Color.Red
}