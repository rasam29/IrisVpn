package com.irisvpn.android.appConfig.theme

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


val SpaceXS = 4.dp
val SpaceS = 8.dp
val SpaceM = 12.dp
val SpaceL = 16.dp
val SpaceXL = 24.dp
val SpaceXXL = 32.dp


val MainToolBarHeight = 72.dp
val SimpleToolBarHeight = 64.dp

val EdgeToEdgeMainToolBarHeight: Dp
    @Composable get() = MainToolBarHeight + WindowInsets.statusBars.asPaddingValues()
        .calculateTopPadding()

val EdgeToEdgeSimpleToolBarHeight: Dp
    @Composable get() = SimpleToolBarHeight + WindowInsets.statusBars.asPaddingValues()
        .calculateTopPadding()

val ToolBarTopPadding: Dp
    @Composable get() = EdgeToEdgeMainToolBarHeight + SpaceXL

val ConnectButtonMargin: Dp = 16.dp
val ConnectButtonPadding: Dp = 16.dp
val ConnectionSpeedHeight = 48.dp
val ConnectionSpeedTextMinWidth = 72.dp
val AppHorizontalMargin = SpaceL


val IconHugeSize = 64.dp
val IconMediumSize = 48.dp
val IconNormalSize = 32.dp
val IconSmallSize = 24.dp
val IconVerySmallSize = 16.dp

val BorderSize = 1.dp
val RoundCornerSize = 12.dp
