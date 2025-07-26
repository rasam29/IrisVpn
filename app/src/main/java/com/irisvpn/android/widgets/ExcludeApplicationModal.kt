package com.irisvpn.android.widgets

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import com.irisvpn.android.appConfig.theme.AppPrimaryColor
import com.irisvpn.android.appConfig.theme.IconMediumSize
import com.irisvpn.android.appConfig.theme.MediumText
import com.irisvpn.android.appConfig.theme.PrimaryTextColor
import com.irisvpn.android.appConfig.theme.SecondaryTextColor
import com.irisvpn.android.appConfig.theme.SmallText
import com.irisvpn.android.appConfig.theme.SpaceL
import com.irisvpn.android.appConfig.theme.SpaceS
import com.irisvpn.android.appConfig.theme.SpaceXS
import com.irisvpn.android.appConfig.theme.SpaceXXL
import com.irisvpn.android.appConfig.theme.TitleMedium
import com.irisvpn.android.screens.excludeApp.ExcludeAppViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExcludeApplicationModal(
    viewModel: ExcludeAppViewModel = koinViewModel(), onDismiss: () -> Unit
) {
    val installedAppState = viewModel.searchedApps.collectAsState(emptyList())
    val querySearchString = viewModel.searchFieldState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getAllInstalledApp()
    }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = onDismiss,
        containerColor = Color.AppPrimaryColor,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (viewModel.loadingInstalledApp.value) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        color = Color.Green, modifier = Modifier.align(alignment = Alignment.Center)
                    )
                }
            } else {
                Box(modifier = Modifier.padding(horizontal = SpaceL)) {
                    Text("Exclude App", style = TitleMedium, color = Color.PrimaryTextColor)
                    Spacer(modifier = Modifier.padding(top = SpaceS))
                    TextField(value = querySearchString.value,
                        onValueChange = {
                            viewModel.updateInput(it)
                        },
                        placeholder = {
                            Text(
                                "Search app...", style = MediumText, color = Color.Black
                            )
                        },
                        trailingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search Icon",
                                tint = Color.Black
                            )
                        },
                        shape = RoundedCornerShape(SpaceXXL),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.LightGray,
                            focusedTextColor = Color.PrimaryTextColor,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.AppPrimaryColor)
                    )
                }
                Spacer(modifier = Modifier.padding(top = SpaceS))
                LazyColumn {
                    installedAppState.value.forEach { app ->
                        item {
                            InstalledApplication(app) {
                                viewModel.toggleExcludeSelectedPackageName(
                                    it.packageName, it.isExcluded
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InstalledApplication(installedApp: InstalledApp, onChecked: (InstalledApp) -> Unit) {
    val isSelected = remember { mutableStateOf(installedApp.isExcluded) }
    val bitmap = remember(installedApp.icon) {
        val drawable = installedApp.icon
        val bmp = Bitmap.createBitmap(32, 32, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        bmp
    }
    Box(
        modifier = Modifier
            .padding(horizontal = SpaceL, vertical = SpaceS)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SpaceS),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.size(IconMediumSize)
            )
            Column(verticalArrangement = Arrangement.Center) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = installedApp.name, style = MediumText, color = Color.PrimaryTextColor
                    )
                    Spacer(modifier = Modifier.padding(start = SpaceS))
                }
                Spacer(modifier = Modifier.padding(top = SpaceXS))
                Text(
                    text = installedApp.packageName,
                    maxLines = 2,
                    style = SmallText,
                    color = Color.SecondaryTextColor
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Checkbox(checked = isSelected.value, onCheckedChange = {
                isSelected.value = it
                onChecked.invoke(installedApp.copy(isExcluded = it))
            })
        }
    }
}


data class InstalledApp(
    val name: String,
    val packageName: String,
    val icon: Drawable,
    val isSystemApp: Boolean,
    val isExcluded: Boolean
)