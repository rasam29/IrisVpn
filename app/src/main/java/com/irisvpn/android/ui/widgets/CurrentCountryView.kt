package com.irisvpn.android.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.irisvpn.android.R

@Composable
fun CurrentCountryView() {
    Box(
        modifier = Modifier
            .background(Color.White.copy(alpha = 0.15F))
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
    ) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.mipmap.german),
                contentDescription = "test",
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.padding(start = 12.dp))
            Column {
                Text(text = "Germany #53", color = Color.White)
                Spacer(modifier = Modifier.padding(top = 12.dp))
                Text(text = "Ip: 188.254.154", color = Color.White)
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.rightarrow),
                contentDescription = "test",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Preview
@Composable
fun CurrentCountryViewPreview() {
    CurrentCountryView()
}