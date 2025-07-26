package com.irisvpn.android.screens.setting

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.irisvpn.android.R
import com.irisvpn.android.appConfig.theme.AppPrimaryColor
import com.irisvpn.android.appConfig.theme.AppTintEffectColor
import com.irisvpn.android.appConfig.theme.BorderSize
import com.irisvpn.android.appConfig.theme.EdgeToEdgeSimpleToolBarHeight
import com.irisvpn.android.appConfig.theme.IconNormalSize
import com.irisvpn.android.appConfig.theme.MediumText
import com.irisvpn.android.appConfig.theme.PrimaryTextColor
import com.irisvpn.android.appConfig.theme.RoundCornerSize
import com.irisvpn.android.appConfig.theme.SecondaryTextColor
import com.irisvpn.android.appConfig.theme.SmallText
import com.irisvpn.android.appConfig.theme.SpaceL
import com.irisvpn.android.appConfig.theme.SpaceS
import com.irisvpn.android.appConfig.theme.SpaceXXL
import com.irisvpn.android.widgets.ExcludeApplicationModal
import com.irisvpn.android.widgets.IrisImageBackground
import com.irisvpn.android.widgets.SimpleToolbar
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingScreen(viewModel: SettingViewModel = koinViewModel()) {
    val context = LocalContext.current
    IrisImageBackground {
        val isShowInstalledAppModal = remember { mutableStateOf(false) }
        SimpleToolbar("Settings", Icons.Default.KeyboardArrowLeft)

        LaunchedEffect(Unit) {
            viewModel.notFetchedError.collect{
                Toast.makeText(context,
                    context.getString(R.string.servers_must_load_to_enable_this_feature), Toast.LENGTH_SHORT).show()
            }
        }
        if (isShowInstalledAppModal.value) {
            ExcludeApplicationModal {
                isShowInstalledAppModal.value = false
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = EdgeToEdgeSimpleToolBarHeight + SpaceXXL, start = SpaceL, end = SpaceL
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.AppTintEffectColor)
                    .border(
                        width = BorderSize,
                        color = Color.White,
                        shape = RoundedCornerShape(RoundCornerSize)
                    )
                    .clickable(onClick = {
                        isShowInstalledAppModal.value = true
                    })
                    .padding(SpaceL)

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column {
                        Text(
                            text = stringResource(R.string.excluded_apps_from_vpn),
                            color = Color.PrimaryTextColor,
                            style = MediumText
                        )
                        Spacer(modifier = Modifier.padding(top = SpaceS))
                        Text(
                            text = stringResource(R.string.apps_you_choose_here_will_bypass_vpn_traffic),
                            color = Color.SecondaryTextColor,
                            style = SmallText
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.rightarrow),
                        contentDescription = "test",
                        tint = Color.PrimaryTextColor,
                        modifier = Modifier.size(IconNormalSize)
                    )
                }
            }

//            Spacer(modifier = Modifier.padding(SpaceS))
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.AppTintEffectColor)
//                    .border(
//                        width = BorderSize,
//                        color = Color.White,
//                        shape = RoundedCornerShape(RoundCornerSize)
//                    )
//                    .clickable(onClick = {
//
//                    })
//                    .padding(SpaceL)
//            ) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    Column(
//                        modifier = Modifier.weight(5F),
//                    ) {
//                        Text(
//                            text = stringResource(R.string.auto_connect_at_startup),
//                            color = Color.PrimaryTextColor,
//                            style = MediumText
//                        )
//                        Spacer(modifier = Modifier.padding(top = SpaceS))
//                        Text(
//                            text = stringResource(R.string.automatically_connects_to_the_selected_server_at_startup_which_may_be_unsuccessful),
//                            color = Color.SecondaryTextColor,
//                            style = SmallText
//                        )
//                    }
//                    Switch(modifier = Modifier.weight(1F),
//                        checked = viewModel.isChecked.value,
//                        colors = SwitchDefaults.colors(
//                            checkedTrackColor = Color.Green,
//                            checkedThumbColor = Color.AppPrimaryColor
//                        ),
//                        onCheckedChange = {
//                            viewModel.onCheckedChange(it)
//                        })
//                }
//            }
        }
    }
}
