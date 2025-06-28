package com.irisvpn.android.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.irisvpn.android.ui.widgets.ConnectToggleView
import com.irisvpn.android.ui.widgets.ConnectionSpeedView
import com.irisvpn.android.ui.widgets.CurrentCountryView
import com.irisvpn.android.ui.widgets.TimerView

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(
            modifier.padding(
                top = 72.dp + WindowInsets.statusBars.asPaddingValues()
                    .calculateTopPadding() + 24.dp
            )
        )
        TimerView()
        CurrentCountryView()
        ConnectionSpeedView()
        Spacer(modifier = Modifier.weight(1f))
        ConnectToggleView(modifier)
    }
}