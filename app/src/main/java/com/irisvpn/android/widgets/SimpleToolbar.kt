package com.irisvpn.android.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.irisvpn.android.appConfig.theme.AppTintEffectColor
import com.irisvpn.android.appConfig.theme.EdgeToEdgeSimpleToolBarHeight
import com.irisvpn.android.appConfig.theme.IconNormalSize
import com.irisvpn.android.appConfig.theme.PrimaryTextColor
import com.irisvpn.android.appConfig.theme.SpaceL
import com.irisvpn.android.appConfig.theme.SpaceS
import com.irisvpn.android.appConfig.theme.ToolBarTitle
import com.irisvpn.android.domain.navigation.IrisNavigation
import com.irisvpn.android.domain.navigation.rememberIrisNavigation
import kotlin.system.exitProcess

@Composable
fun SimpleToolbar(title: String, leftIcon: ImageVector, irisNavigation: IrisNavigation = rememberIrisNavigation()) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.AppTintEffectColor)) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(EdgeToEdgeSimpleToolBarHeight),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(WindowInsets.statusBars.asPaddingValues())
                    .padding(horizontal = SpaceS, vertical = SpaceL)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)

            ) {
                Text(style = ToolBarTitle, text = title, color = Color.PrimaryTextColor,modifier = Modifier.align(Alignment.Center))
                Icon(
                    modifier = Modifier.size(IconNormalSize).align(Alignment.CenterStart).clickable{
                        irisNavigation.backPressed { exitProcess(0) }
                    },
                    imageVector = leftIcon,
                    contentDescription = "",
                    tint = Color.PrimaryTextColor
                )
            }
        }
    }
}