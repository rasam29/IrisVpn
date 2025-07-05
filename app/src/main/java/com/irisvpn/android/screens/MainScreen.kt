package com.irisvpn.android.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.irisvpn.android.androidSpecific.rememberPlatformState
import com.irisvpn.android.appConfig.TelegramChannel
import com.irisvpn.android.appConfig.theme.SpaceS
import com.irisvpn.android.appConfig.theme.SpaceXL
import com.irisvpn.android.utils.navigation.Screen
import com.irisvpn.android.utils.navigation.rememberIrisNavigation
import com.irisvpn.android.widgets.ConnectToggleView
import com.irisvpn.android.widgets.ConnectionSpeedView
import com.irisvpn.android.widgets.CurrentCountryView
import com.irisvpn.android.widgets.IrisImageBackground
import com.irisvpn.android.widgets.MainToolBar
import com.irisvpn.android.widgets.SelectCountryModal
import com.irisvpn.android.widgets.TimerView

@Composable
fun MainScreen() {
    IrisImageBackground {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(SpaceXL),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val platformState = rememberPlatformState()
            val irisNavigation = rememberIrisNavigation()
            MainToolBar(telegramClick = {
                platformState.launchTelegram(TelegramChannel)
            }, settingClick = {
                irisNavigation.navigate(Screen.Setting())
            }, {})
            Spacer(
                Modifier.padding(
                    top = SpaceS
                )
            )
            val showDialog = remember { mutableStateOf(false) }
            val platform = rememberPlatformState()

            TimerView()
            CurrentCountryView {
                showDialog.value = true
            }
            ConnectionSpeedView()
            Spacer(modifier = Modifier.weight(1f))
            ConnectToggleView {
                platform.effectOnConnect()
            }
            if (showDialog.value) {
                SelectCountryModal {
                    showDialog.value = false
                }
            }
        }
    }
}