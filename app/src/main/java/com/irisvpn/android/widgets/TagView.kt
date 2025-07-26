package com.irisvpn.android.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.irisvpn.android.appConfig.theme.AppTintEffectColor
import com.irisvpn.android.appConfig.theme.SmallText
import com.irisvpn.android.appConfig.theme.SpaceM
import com.irisvpn.android.appConfig.theme.SpaceS
import com.irisvpn.android.appConfig.theme.SpaceXS
import com.irisvpn.android.appConfig.theme.VerySmallText

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IrisTagView(tags: String) {
    val tags = tags.split(",").map { it.trim() }
    FlowRow(
        maxLines = 1,
        horizontalArrangement = Arrangement.spacedBy(SpaceXS),
        maxItemsInEachRow = 5
    ) {
        tags.forEach { tag ->
            TagChip(tag)
        }
    }
}

@Composable
private fun TagChip(tag: String) {
    Box(
        modifier = Modifier
            .background(Color.AppTintEffectColor, shape = RoundedCornerShape(SpaceM))
            .padding(horizontal = SpaceM, vertical = SpaceXS)
    ) {
        Text(text = tag, color = Color.White, style = VerySmallText)
    }
}