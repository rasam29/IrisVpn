package com.irisvpn.android.widgets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.irisvpn.android.R
import com.irisvpn.android.appConfig.theme.AppTintEffectColor
import com.irisvpn.android.appConfig.theme.IconBackGround
import com.irisvpn.android.appConfig.theme.PrimaryTextColor
import com.irisvpn.android.appConfig.theme.SpaceS
import com.irisvpn.android.appConfig.theme.ToolBarTitle
import com.irisvpn.android.appConfig.theme.EdgeToEdgeMainToolBarHeight

@Composable
fun MainToolBar(
    telegramClick: () -> Unit,
    settingClick: () -> Unit,
    onPremiumClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(EdgeToEdgeMainToolBarHeight)
            .background(Color.AppTintEffectColor)
    ) {
        Box(
            modifier = Modifier
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(SpaceS)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Row(modifier = Modifier.align(Alignment.CenterStart)) {
                SimpleToolbarIcon(
                    click = telegramClick,
                    contentDescription = "telegram",
                    icon = R.drawable.telegram,
                )
                ToolBarIcon(
                    click  = {
                        settingClick.invoke()
                    }, contentDescription = "setting", icon = R.drawable.setting
                )
            }
            Row(modifier = Modifier.align(Alignment.Center)) {
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "telegram",
                    modifier = Modifier.size(48.dp),
                    tint = Color.White
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = Color.PrimaryTextColor,
                    style = ToolBarTitle,
                    text = stringResource(R.string.app_complete_name)
                )
            }

            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
//                ToolBarIcon(
//                    click = addCustomSetting,
//                    contentDescription = "customVpnConfig",
//                    icon = R.mipmap.add
//                )
                ToolBarIcon(
                    click = onPremiumClick, contentDescription = "crown", icon = R.drawable.crown
                )
            }
        }
    }
}

@Composable
fun ToolBarIcon(
    click: () -> Unit, size: Dp = 24.dp, contentDescription: String, @DrawableRes icon: Int
) {
    IconButton(onClick = click, modifier = Modifier.size(48.dp)) {
        Box(
            Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color.IconBackGround)
                .padding(6.dp)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = contentDescription,
                modifier = Modifier.size(size)
            )
        }
    }
}

@Composable
fun SimpleToolbarIcon(
    click: () -> Unit, size: Dp = 32.dp, contentDescription: String, @DrawableRes icon: Int
) {
    IconButton(onClick = click, modifier = Modifier.size(52.dp)) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
            modifier = Modifier.size(size)
        )
    }
}