package com.irisvpn.android.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.irisvpn.android.appConfig.theme.PrimaryTextColor
import com.irisvpn.android.appConfig.theme.SecondaryTextColor
import com.irisvpn.android.appConfig.theme.TitleLarge
import com.irisvpn.android.appConfig.theme.TitleMedium
import com.irisvpn.android.domain.vpn.ConnectionTime

@Composable
fun TimerView(timer: ConnectionTime) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Connection time", color = Color.SecondaryTextColor, style = TitleMedium)
        Text(text = timer.time, color = Color.PrimaryTextColor, style = TitleLarge)
    }
}