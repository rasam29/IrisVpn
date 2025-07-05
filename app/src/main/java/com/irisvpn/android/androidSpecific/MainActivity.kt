package com.irisvpn.android.androidSpecific

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.irisvpn.android.R
import com.irisvpn.android.appConfig.TelegramChannel
import com.irisvpn.android.screens.MainScreen
import com.irisvpn.android.utils.navigation.Screen
import com.irisvpn.android.utils.navigation.rememberIrisNavigation
import com.irisvpn.android.widgets.MainToolBar
import dev.dev7.lib.v2ray.V2rayController

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        V2rayController.init(this, R.mipmap.ic_launcher,resources.getString(R.string.app_name))
//        V2rayController.startV2ray(
//            this,
//            "Test Server",
//            "vless://d2051c83-df56-4002-a7ce-0cfb5e8b85cc@217.60.38.27:8001?type=tcp&security=none#HongKong-VLESS-vl-rasam",
//            null
//        )
        enableEdgeToEdge()
        setContent {
            val irisNavigation = rememberIrisNavigation()
            val platformState = rememberPlatformState()
            onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    irisNavigation.backPressed {
                        platformState.closeApplication()
                    }
                }
            })
            Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            } ) {  innerPadding ->
                irisNavigation.RenderCurrentScreen()
            }
        }
    }
}