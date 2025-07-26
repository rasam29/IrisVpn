package com.irisvpn.android.screens.main

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.android.play.core.review.ReviewManagerFactory
import com.irisvpn.android.R
import com.irisvpn.android.androidSpecific.hasNotificationPermission
import com.irisvpn.android.androidSpecific.isPreparedForConnection
import com.irisvpn.android.androidSpecific.prepareForConnection
import com.irisvpn.android.androidSpecific.rememberPlatformState
import com.irisvpn.android.androidSpecific.requestNotification
import com.irisvpn.android.appConfig.TelegramChannel
import com.irisvpn.android.appConfig.theme.SpaceS
import com.irisvpn.android.appConfig.theme.SpaceXL
import com.irisvpn.android.domain.core.GeneralState
import com.irisvpn.android.domain.navigation.Screen
import com.irisvpn.android.domain.navigation.rememberIrisNavigation
import com.irisvpn.android.domain.platform.AdManager
import com.irisvpn.android.domain.vpn.ConnectionStats
import com.irisvpn.android.utils.findActivity
import com.irisvpn.android.widgets.ConnectToggleView
import com.irisvpn.android.widgets.ConnectionSpeedView
import com.irisvpn.android.widgets.CurrentCountryView
import com.irisvpn.android.widgets.IrisImageBackground
import com.irisvpn.android.widgets.MainToolBar
import com.irisvpn.android.widgets.SelectCountryModal
import com.irisvpn.android.widgets.TimerView
import dev.dev7.lib.v2ray.V2rayController
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import java.util.ArrayList

@Composable
fun MainScreen(adsManager: AdManager = koinInject(), viewModel: MainViewModel = koinViewModel()) {
    val stats = viewModel.connectionStats.collectAsState(ConnectionStats.DisConnected())
    val generalState = viewModel.generalState.collectAsState(GeneralState.DisConnected)
    val currentServer = viewModel.currentServer.collectAsState()

    val context = LocalContext.current
    val activity = context.findActivity()!!

    val tunnelDevicePermissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { isGranted ->
            V2rayController.startV2ray(
                activity,
                "Test",
                "vless://405bdbcef0433592acdd371b9337fc0520bd627a480f813c0ba49de96e7ec61b@217.60.38.27:8001?type=tcp&security=none#HongKong-VLESS-api_405bdbcef0433592acdd371b9337fc0520bd627a480f813c0ba49de96e7ec61b",
                ArrayList()
            )
            if (isPreparedForConnection(context)) {
                viewModel.toggleConnection()
            } else {
                Toast.makeText(context, R.string.permission_required, Toast.LENGTH_SHORT).show()
            }
        }

    fun ensureTunnelPermissionAndToggle() {
        if (isPreparedForConnection(context)) {
            viewModel.toggleConnection()
        } else {
            prepareForConnection(activity, tunnelDevicePermissionLauncher)
        }
    }

    val state = generalState.value
    when (state) {
        GeneralState.Connecting -> {

        }

        GeneralState.DisConnected -> {

        }

        GeneralState.DisConnectedOfAdShowFailed -> {
            Toast.makeText(
                context,
                stringResource(R.string.failed_to_show_advertisement),
                Toast.LENGTH_SHORT
            ).show()
        }

        GeneralState.DisConnectedOfDismiss -> {
            Toast.makeText(
                context,
                stringResource(R.string.must_see_advertisement_to_continue), Toast.LENGTH_SHORT
            )
                .show()
        }

        GeneralState.MustSeeAdToContinue -> {
            LaunchedEffect(Unit) {
                val state = adsManager.show()
                viewModel.adFinished(state)
            }
        }

        is GeneralState.SessionFetchError -> {
            Toast.makeText(
                context,
                stringResource(R.string.failed_to_fetch_server_data),
                Toast.LENGTH_SHORT
            ).show()
        }

        GeneralState.WaitForServerListToLoad -> {
            Toast.makeText(
                context,
                stringResource(R.string.please_wait_until_servers_load_successfully),
                Toast.LENGTH_SHORT
            ).show()

        }

        is GeneralState.Connected -> {
            if (state.showRate) {
                val manager = ReviewManagerFactory.create(context)
                val request = manager.requestReviewFlow()
                request.addOnCompleteListener { request ->
                    if (request.isSuccessful) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.thank_you_for_your_feedback),
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                    }
                }
            }
        }
    }

    val notificationPermissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
//            if (!hasNotificationPermission(activity)) {
//                Toast.makeText(
//                    context,
//                    R.string.need_notification_permission_for_better_experience,
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
            ensureTunnelPermissionAndToggle()
        }

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
            }, onPremiumClick = {
                Toast.makeText(context, R.string.coming_soon, Toast.LENGTH_SHORT).show()
            })
            Spacer(
                Modifier.padding(
                    top = SpaceS
                )
            )
            val showDialog = remember { mutableStateOf(false) }
            val platform = rememberPlatformState()

            TimerView(stats.value.time)
            CurrentCountryView(currentServer.value, {
                viewModel.retryRefreshingServer()
            }) {
                showDialog.value = true
            }
            ConnectionSpeedView(stats.value.speed)
            Spacer(modifier = Modifier.weight(1f))
            ConnectToggleView(generalState.value) {
                platform.effectOnConnect()
                if (hasNotificationPermission(activity)) {
                    ensureTunnelPermissionAndToggle()
                } else {
                    requestNotification(activity, notificationPermissionLauncher)
                }
            }
            if (showDialog.value) {
                SelectCountryModal {
                    showDialog.value = false
                }
            }
        }
    }
}