package com.irisvpn.android.widgets


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.irisvpn.android.R
import com.irisvpn.android.appConfig.theme.AppPrimaryColor
import com.irisvpn.android.appConfig.theme.IconHugeSize
import com.irisvpn.android.appConfig.theme.IconNormalSize
import com.irisvpn.android.appConfig.theme.IconVerySmallSize
import com.irisvpn.android.appConfig.theme.MediumText
import com.irisvpn.android.appConfig.theme.PrimaryTextColor
import com.irisvpn.android.appConfig.theme.RoundCornerSize
import com.irisvpn.android.appConfig.theme.SecondaryTextColor
import com.irisvpn.android.appConfig.theme.SmallText
import com.irisvpn.android.appConfig.theme.SpaceL
import com.irisvpn.android.appConfig.theme.SpaceS
import com.irisvpn.android.appConfig.theme.SpaceXS
import com.irisvpn.android.appConfig.theme.SpaceXXL
import com.irisvpn.android.appConfig.theme.TitleMedium
import com.irisvpn.android.appConfig.theme.VerySmallText
import com.irisvpn.android.domain.core.CurrentServerState
import com.irisvpn.android.domain.server.AvailableServer
import com.irisvpn.android.screens.main.MainViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCountryModal(viewModel: MainViewModel = koinViewModel(), onDismiss: () -> Unit) {
    val currentServer = viewModel.currentServer.collectAsState()
    val availableServer = viewModel.allAvailableServer.collectAsState(emptyList())
    ModalBottomSheet(onDismissRequest = onDismiss, containerColor = Color.AppPrimaryColor) {
        Box(modifier = Modifier.fillMaxSize()) {
            val currentState = currentServer.value
            when (currentState) {
                is CurrentServerState.OnError -> ErrorMessageWithRetry(currentState.message) { viewModel.retryRefreshingServer() }
                CurrentServerState.Loading -> ShimmerLoading()
                is CurrentServerState.OnReady -> SuccessAvailableServerItem(
                    availableServer.value,
                    currentState.current
                ) {
                    viewModel.selectServer(it)
                    onDismiss.invoke()
                }
            }
        }
    }
}


@Composable
private fun ErrorMessageWithRetry(message: String, onRetryClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SpaceL)
            .clickable { onRetryClick.invoke() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SpaceS)
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Connection error",
            tint = Color.Red.copy(alpha = 0.5f),
            modifier = Modifier.size(IconHugeSize)
        )
        Text(
            text = stringResource(R.string.available_server_error_title),
            style = MediumText,
            color = Color.PrimaryTextColor
        )
        Text(text = message, style = SmallText, color = Color.SecondaryTextColor)

        Icon(imageVector = Icons.Default.Refresh,
            contentDescription = "Retry connection",
            tint = Color.PrimaryTextColor.copy(alpha = 0.6f),
            modifier = Modifier
                .size(IconNormalSize)
                .clickable {
                    onRetryClick.invoke()
                })
    }
}

@Composable
private fun ShimmerLoading() {
    LazyColumn {
        repeat(5) {
            item {
                LoadingItem()
            }
        }
    }
}

@Composable
private fun LoadingItem() {
    Box(
        modifier = Modifier
            .padding(SpaceL)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SpaceS),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Country flag placeholder
            Box(
                modifier = Modifier
                    .size(IconHugeSize)
                    .clip(RoundedCornerShape(SpaceXS))
                    .background(Color.Gray.copy(alpha = 0.3f))
                    .shimmerEffect()
            )

            Column(verticalArrangement = Arrangement.Center) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .width(IconHugeSize)
                            .height(SpaceL)
                            .clip(CircleShape)
                            .background(Color.Gray.copy(alpha = 0.3f))
                            .shimmerEffect()
                    )

                    Spacer(modifier = Modifier.padding(start = SpaceS))
                }

                Spacer(modifier = Modifier.padding(top = SpaceXS))
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(14.dp)
                        .clip(RoundedCornerShape(7.dp))
                        .background(Color.Gray.copy(alpha = 0.2f))
                        .shimmerEffect()
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .width(IconHugeSize)
                    .height(SpaceXXL)
                    .clip(RoundedCornerShape(RoundCornerSize))
                    .background(Color.Gray.copy(alpha = 0.2f))
                    .shimmerEffect()
            )
        }
    }
}

@Composable
private fun SuccessAvailableServerItem(
    list: List<AvailableServer>,
    currentSelectedServer: AvailableServer,
    onSelect: (AvailableServer) -> Unit
) {
    LazyColumn {
        list.forEach { server ->
            item {
                AvailableServerItem(server, server.id == currentSelectedServer.id) {
                    onSelect.invoke(it)
                }
            }
        }
    }
}

@Composable
private fun AvailableServerItem(
    data: AvailableServer,
    isSelected: Boolean,
    selectServer: (AvailableServer) -> Unit
) {
    Box(
        modifier = Modifier
            .background(if (isSelected) Color.Green.copy(alpha = 0.1F) else Color.Transparent)
            .clip(RoundedCornerShape(size = RoundCornerSize))
            .clickable {
                selectServer.invoke(data)
            }
            .padding(SpaceL)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SpaceS),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = data.flag,
                contentDescription = "test",
                modifier = Modifier
                    .size(IconHugeSize)
                    .clip(CircleShape)
            )
            Column(verticalArrangement = Arrangement.Center) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = data.name ?: "",
                        style = TitleMedium,
                        color = Color.PrimaryTextColor
                    )
                    Spacer(modifier = Modifier.padding(start = SpaceS))
                    if (false) {//premium tag for the future!
                        Text(
                            text = stringResource(R.string.premium),
                            style = VerySmallText,
                            color = Color.Yellow
                        )
                        Spacer(modifier = Modifier.padding(start = SpaceXS))
                        Image(
                            painter = painterResource(R.drawable.crown),
                            contentDescription = "crown",
                            modifier = Modifier.size(IconVerySmallSize)
                        )
                    }
                }
                if (!(data.tags.isNullOrBlank())) {
                    Spacer(modifier = Modifier.padding(top = SpaceXS))
                    IrisTagView(data.tags ?: "")
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = data.ping.value,
                style = MediumText,
                color = data.ping.color,
            )
        }
    }
}

