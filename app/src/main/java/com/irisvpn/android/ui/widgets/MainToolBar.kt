package com.irisvpn.android.ui.widgets

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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.irisvpn.android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainToolBar(
    telegramClick: () -> Unit,
    settingClick: () -> Unit,
    addCustomSetting: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(72.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
            .background(Color.White.copy(alpha = 0.15F))
    ) {
        Box(
            modifier = Modifier
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(8.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Row(modifier = Modifier.align(Alignment.CenterStart)) {
                SimpleToolbarIcon(
                    click = telegramClick,
                    contentDescription = "telegram",
                    icon = R.mipmap.telegram,
                )
                ToolBarIcon(
                    click = settingClick, contentDescription = "setting", icon = R.mipmap.setting
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
                    style = TextStyle.Default.copy(Color.White, fontSize = 20.sp),
                    text = stringResource(R.string.app_complete_name)
                )
            }

            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                ToolBarIcon(
                    click = addCustomSetting,
                    contentDescription = "customVpnConfig",
                    icon = R.mipmap.add
                )
                ToolBarIcon(
                    click = addCustomSetting, contentDescription = "crown", icon = R.mipmap.crown
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
                .background(Color.Gray.copy(alpha = 0.5f))
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