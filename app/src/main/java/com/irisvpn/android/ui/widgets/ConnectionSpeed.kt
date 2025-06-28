package com.irisvpn.android.ui.widgets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import com.irisvpn.android.R

@Composable
fun ConnectionSpeedView(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        SpeedItem(
            modifier = Modifier.weight(1f),
            title = stringResource(R.string.download),
            icon = R.drawable.download
        )
        VerticalDivider(modifier = Modifier.height(48.dp),thickness = 1.dp)
        SpeedItem(
            modifier = Modifier.weight(1f),
            title = stringResource(R.string.upload),
            icon = R.drawable.upload
        )
    }
}

@Composable
private fun SpeedItem(modifier: Modifier, title: String, @DrawableRes icon: Int) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(icon),
            contentDescription = "test",
            tint = Color.White,
            modifier = Modifier.size(32.dp)
        )
        Column(
            modifier = Modifier.widthIn(72.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, color = Color.Gray)
            Text("0 KB/s", color = Color.Gray)
        }
    }
}