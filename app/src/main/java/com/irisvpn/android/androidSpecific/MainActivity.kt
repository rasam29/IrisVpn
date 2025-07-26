package com.irisvpn.android.androidSpecific

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.irisvpn.android.domain.navigation.rememberIrisNavigation

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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