package com.irisvpn.android.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.irisvpn.android.R
import com.irisvpn.android.appConfig.theme.BackGroundColorCode

@Composable
fun IrisImageBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.BackGroundColorCode)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.worldmap),
            contentDescription = "My Vector Icon",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = 2f,
                    scaleY = 2f,
                    translationX = 150f, //  left (negative), right (positive)
                    translationY = -160f //down (positive), up (negative)
                )
        )
        content()
    }
}