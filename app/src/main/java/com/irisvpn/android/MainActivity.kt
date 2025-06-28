package com.irisvpn.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.irisvpn.android.screen.MainScreen
import com.irisvpn.android.ui.theme.IrisVpnTheme
import com.irisvpn.android.ui.widgets.MainToolBar

class MainActivity : ComponentActivity() {
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
            IrisVpnTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    MainToolBar({},{},{})
                }) { innerPadding ->
                    Box (modifier = Modifier.fillMaxSize()){
                        Image(
                            painter = painterResource(id = R.mipmap.main_background), // vector drawable
                            contentDescription = "My Vector Icon",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        MainScreen()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IrisVpnTheme {
//        Greeting("Android")
    }
}