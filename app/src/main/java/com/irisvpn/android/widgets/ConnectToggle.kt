package com.irisvpn.android.widgets

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.irisvpn.android.R
import com.irisvpn.android.appConfig.theme.AppTintEffectColor
import com.irisvpn.android.appConfig.theme.IconMediumSize
import com.irisvpn.android.appConfig.theme.IconNormalSize
import com.irisvpn.android.appConfig.theme.IconSmallSize
import com.irisvpn.android.appConfig.theme.SpaceXL
import com.irisvpn.android.appConfig.theme.SpaceXS

@Composable
fun ConnectToggleView(onConnectClick: () -> Unit) {
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
        ConnectButton(onConnectClick, false)
    }
}

@Composable
private fun ConnectButton(
    onClick: () -> Unit, isConnected: Boolean
) {
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(190.dp)
                .background(Color(0xFF1B1E3C), shape = CircleShape) // outer circle
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
                imageVector = ImageVector.vectorResource(R.drawable.power), // You can replace this with a custom SVG
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
                imageVector = ImageVector.vectorResource(R.drawable.unprotected),
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(IconSmallSize)
            )
            Spacer(modifier = Modifier.width(SpaceXS))
            Text(
                text = if (isConnected) stringResource(R.string.connected) else stringResource(R.string.not_connected),
                color = if (isConnected) Color.Green else Color.Red
            )
        }
    }
}

@Preview
@Composable
fun PreviewToggle(modifier: Modifier = Modifier) {
    ConnectToggleView {}
}