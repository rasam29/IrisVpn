package com.irisvpn.android.domain.core

import com.irisvpn.android.androidSpecific.AdState
import com.irisvpn.android.domain.server.AvailableServer
import com.irisvpn.android.domain.vpn.ConnectionStats
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


sealed interface GeneralState {
    class Connected(val showRate: Boolean) : GeneralState
    data object Connecting : GeneralState
    data object DisConnected : GeneralState
    data object DisConnectedOfAdShowFailed : GeneralState
    data object DisConnectedOfDismiss : GeneralState
    data object MustSeeAdToContinue : GeneralState
    data object WaitForServerListToLoad : GeneralState
    data class SessionFetchError(val errorMessage: String): GeneralState
}

sealed interface CurrentServerState {
    class OnReady(val current: AvailableServer) : CurrentServerState
    class OnError(val message: String) : CurrentServerState
    data object Loading: CurrentServerState
}


interface IrisUseCase {

    val generalState: Flow<GeneralState>

    val allAvailableServer:Flow<List<AvailableServer>>

    fun currentIrisServer(scope: CoroutineScope): StateFlow<CurrentServerState>

    val connectionStats: Flow<ConnectionStats>

    fun toggleConnection(coroutineScope: CoroutineScope)

    fun adFinished(adState: AdState)

    fun refreshServerList(scope: CoroutineScope)

    fun selectServer(server: AvailableServer)
}