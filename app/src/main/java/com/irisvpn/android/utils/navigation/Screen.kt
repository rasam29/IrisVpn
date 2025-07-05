package com.irisvpn.android.utils.navigation

import androidx.compose.runtime.Composable
import com.irisvpn.android.screens.AddConfigScreen
import com.irisvpn.android.screens.BuyPremiumScreen
import com.irisvpn.android.screens.MainScreen
import com.irisvpn.android.screens.SettingScreen

sealed interface Screen {
    @Composable
    fun content(): @Composable () -> Unit

    class Setting() : Screen {
        @Composable
        override fun content(): @Composable (() -> Unit) =
            (@Composable { -> SettingScreen() })
    }

    class Main() : Screen {
        @Composable
        override fun content(): @Composable (() -> Unit) =
            (@Composable { -> MainScreen() })
    }
    class Add() : Screen {
        @Composable
        override fun content(): @Composable (() -> Unit) =
            (@Composable { -> AddConfigScreen() })
    }

    class Premium() : Screen {
        @Composable
        override fun content(): @Composable (() -> Unit) =
            (@Composable { -> BuyPremiumScreen() })
    }
}