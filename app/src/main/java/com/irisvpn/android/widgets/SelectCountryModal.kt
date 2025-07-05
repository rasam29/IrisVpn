package com.irisvpn.android.widgets


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.irisvpn.android.R
import com.irisvpn.android.appConfig.theme.AppPrimaryColor
import com.irisvpn.android.appConfig.theme.BorderSize
import com.irisvpn.android.appConfig.theme.IconHugeSize
import com.irisvpn.android.appConfig.theme.IconVerySmallSize
import com.irisvpn.android.appConfig.theme.PrimaryTextColor
import com.irisvpn.android.appConfig.theme.RoundCornerSize
import com.irisvpn.android.appConfig.theme.SecondaryTextColor
import com.irisvpn.android.appConfig.theme.SmallText
import com.irisvpn.android.appConfig.theme.SpaceL
import com.irisvpn.android.appConfig.theme.SpaceS
import com.irisvpn.android.appConfig.theme.SpaceXS
import com.irisvpn.android.appConfig.theme.TitleMedium
import com.irisvpn.android.appConfig.theme.VerySmallText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCountryModal(onDismiss: () -> Unit) {
    ModalBottomSheet(onDismissRequest = onDismiss, containerColor = Color.AppPrimaryColor) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                repeat(100) {
                    item {
                        AvailableServerItem(it%2 == 0)
                    }
                }
            }
        }
    }
}

@Composable
fun AvailableServerItem(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .padding(SpaceL)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(SpaceS), verticalAlignment = Alignment.CenterVertically
        ) {
            ToolBarIcon(
                icon = R.mipmap.german, contentDescription = "", click = {}, size = IconHugeSize
            )
            Column(verticalArrangement = Arrangement.Center) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Germany", style = TitleMedium, color = Color.PrimaryTextColor)
                    Spacer(modifier = Modifier.padding(start = SpaceS))
                    Text(text = "PREMIUM", style = VerySmallText, color = Color.Yellow)
                    Spacer(modifier = Modifier.padding(start = SpaceXS))
                    Image(
                        painter = painterResource(R.mipmap.crown),
                        contentDescription = "Power Button",
                        modifier = Modifier.size(IconVerySmallSize)
                    )
                }
                Spacer(modifier = Modifier.padding(top = SpaceXS))
                Text(text = "your custom tag here", style = SmallText, color = Color.SecondaryTextColor)
            }
            Spacer(modifier = Modifier.weight(1f))
            if (isSelected) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(SpaceXS)
                ) {
                    Text(text = "Selected", style = SmallText, color = Color.Green)
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Power Button",
                        tint = Color.Green,
                        modifier = Modifier.size(IconVerySmallSize)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .border(
                            width = BorderSize,
                            color = Color.White,
                            shape = RoundedCornerShape(RoundCornerSize)
                        )
                        .padding(SpaceS)
                ) {
                    Text(text = "Connect", style = SmallText, color = Color.PrimaryTextColor)
                }
            }
        }
    }
}