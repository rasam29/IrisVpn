package com.irisvpn.android.widgets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.irisvpn.android.R
import com.irisvpn.android.appConfig.theme.AppHorizontalMargin
import com.irisvpn.android.appConfig.theme.ConnectionSpeedHeight
import com.irisvpn.android.appConfig.theme.ConnectionSpeedTextMinWidth
import com.irisvpn.android.appConfig.theme.IconNormalSize
import com.irisvpn.android.appConfig.theme.MediumText
import com.irisvpn.android.appConfig.theme.PrimaryTextColor
import com.irisvpn.android.appConfig.theme.SecondaryTextColor
import com.irisvpn.android.appConfig.theme.SmallText
import com.irisvpn.android.appConfig.theme.SpaceXS
import com.irisvpn.android.domain.vpn.Speed

@Composable
fun ConnectionSpeedView(speed:Speed) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppHorizontalMargin)
            .size(ConnectionSpeedHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        SpeedItem(
            modifier = Modifier.weight(1f),
            title = stringResource(R.string.download),
            icon = R.drawable.download,
            value = speed.download
        )
        VerticalDivider(modifier = Modifier.fillMaxHeight())
        SpeedItem(
            modifier = Modifier.weight(1f),
            title = stringResource(R.string.upload),
            icon = R.drawable.upload,
            value = speed.upload
        )
    }
}

@Composable
private fun SpeedItem(value:String,modifier: Modifier, title: String, @DrawableRes icon: Int) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(icon),
            contentDescription = "test",
            tint = Color.White,
            modifier = Modifier.size(IconNormalSize)
        )
        Column(
            modifier = Modifier.widthIn(ConnectionSpeedTextMinWidth),
            verticalArrangement = Arrangement.spacedBy(SpaceXS),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, color = Color.SecondaryTextColor, style = SmallText)
            Text(text = value, color = Color.PrimaryTextColor, style = MediumText)
        }
    }
}


@Composable
@Preview
fun ConnectionSpeed() {
    ConnectionSpeedView(Speed(
        upload = "21 kb/s",
        download = "23 kb/s"
    ))
}