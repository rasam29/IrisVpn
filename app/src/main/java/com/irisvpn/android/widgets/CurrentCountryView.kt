package com.irisvpn.android.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.irisvpn.android.R
import com.irisvpn.android.appConfig.theme.AppHorizontalMargin
import com.irisvpn.android.appConfig.theme.AppTintEffectColor
import com.irisvpn.android.appConfig.theme.BorderSize
import com.irisvpn.android.appConfig.theme.IconHugeSize
import com.irisvpn.android.appConfig.theme.IconNormalSize
import com.irisvpn.android.appConfig.theme.MediumText
import com.irisvpn.android.appConfig.theme.PrimaryTextColor
import com.irisvpn.android.appConfig.theme.RoundCornerSize
import com.irisvpn.android.appConfig.theme.SecondaryTextColor
import com.irisvpn.android.appConfig.theme.SmallText
import com.irisvpn.android.appConfig.theme.SpaceM
import com.irisvpn.android.appConfig.theme.SpaceS

@Composable
fun CurrentCountryView(onCLick:()->Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppHorizontalMargin)
            .background(Color.AppTintEffectColor)
            .border(
                width = BorderSize,
                color = Color.White,
                shape = RoundedCornerShape(RoundCornerSize)
            ).clickable(onClick = onCLick)
    ) {
        Row(modifier = Modifier.padding(SpaceS), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.mipmap.german),
                contentDescription = "test",
                modifier = Modifier.size(IconHugeSize)
            )
            Spacer(modifier = Modifier.padding(start = SpaceM))
            Column {
                Text(text = "Germany #53", color = Color.PrimaryTextColor, style = MediumText)
                Spacer(modifier = Modifier.padding(top = SpaceS))
                Text(text = "Ip: 188.254.154", color = Color.SecondaryTextColor, style = SmallText)
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.rightarrow),
                contentDescription = "test",
                tint = Color.PrimaryTextColor,
                modifier = Modifier.size(IconNormalSize)
            )
        }
    }
}

@Preview
@Composable
fun CurrentCountryViewPreview() {

}