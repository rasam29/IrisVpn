package com.irisvpn.android.domain.core

import com.irisvpn.android.androidSpecific.AdState
import com.irisvpn.android.appConfig.SHOW_RATE_ON_N_SUCCESS
import com.irisvpn.android.domain.analytics.AnalyticService
import com.irisvpn.android.domain.core.GeneralState.*
import com.irisvpn.android.domain.platform.ConnectCountRepository
import com.irisvpn.android.domain.server.AvailableServer
import com.irisvpn.android.domain.server.ServerRepository
import com.irisvpn.android.domain.server.ServerRetriever
import com.irisvpn.android.domain.session.SessionRepository
import com.irisvpn.android.domain.vpn.ConnectionStats
import com.irisvpn.android.domain.vpn.IrisConnectionState
import com.irisvpn.android.domain.vpn.VpnService
import com.irisvpn.android.utils.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class IrisUseCaseImpl(
    private val sessionService: SessionRepository,
    private val vpnService: VpnService,
    private val serverRepository: ServerRepository,
    private val analyticService: AnalyticService,
    private val countRepository: ConnectCountRepository,
    private val serverRetriever: ServerRetriever
) : IrisUseCase {

    init {
        vpnService.init()
    }

    private val shouldShowRate: Boolean get() = countRepository.getSuccessfulConnectCount() == SHOW_RATE_ON_N_SUCCESS

    private val _generalState = MutableStateFlow<GeneralState>(DisConnected)

    override fun currentIrisServer(scope: CoroutineScope): StateFlow<CurrentServerState> =
        serverRepository.getCurrentSelectedServer(scope, false)

    override val generalState: Flow<GeneralState> = _generalState

    override val allAvailableServer: Flow<List<AvailableServer>>
        get() = serverRepository.getAllAvailableServer()

    override val connectionStats: Flow<ConnectionStats> = vpnService.onReceiveCurrentState

    override fun toggleConnection(coroutineScope: CoroutineScope) {
        when (vpnService.getConnectionState()) {
            IrisConnectionState.CONNECTED -> {
                vpnService.stop()
                analyticService.disConnectedManually()
                countRepository.incrementConnectCount()
            }

            IrisConnectionState.CONNECTING -> {}

            IrisConnectionState.DISCONNECTED -> {
                coroutineScope.launch {
                    analyticService.connectAttempt()
                    val currentConfig =
                        serverRepository.getCurrentSelectedServer(coroutineScope, false).value
                    if (currentConfig is CurrentServerState.OnReady) {
                        _generalState.value = Connecting
                        val newConfig =
                            sessionService.getSessionFromServer(currentConfig.current.id.toString())
                        when (newConfig) {
                            is Either.Left -> {
                                analyticService.sessionConfigFetchFailed()
                                _generalState.value =
                                    SessionFetchError(newConfig.value.message ?: "")
                            }

                            is Either.Right -> {
                                vpnService.start(currentConfig.current.name ?: "", newConfig.value)
                                _generalState.value = MustSeeAdToContinue
                            }
                        }

                    } else {
                        analyticService.serverListNotLoaded()
                    }
                }
            }
        }
    }

    override fun adFinished(adState: AdState) {
        when (adState) {
            AdState.Dismissed -> {
                vpnService.stop()
                analyticService.adDismissed()
                _generalState.value = DisConnectedOfDismiss
            }

            is AdState.FailedToLoad -> {
                vpnService.stop()
                analyticService.adShowFailed()
                _generalState.value = DisConnectedOfAdShowFailed
            }

            AdState.FailedToShow -> {
                vpnService.stop()
                analyticService.adShowFailed()
                _generalState.value = DisConnectedOfAdShowFailed
            }

            AdState.Seen -> {
                countRepository.incrementConnectCount()
                analyticService.succussfullConnect()
                _generalState.value = Connected(shouldShowRate)
            }
        }

    }

    override fun refreshServerList(scope: CoroutineScope) {
        scope.launch {
            serverRepository.setCurrentSelectedServer(
                CurrentServerState.Loading
            )
            val serverList = serverRetriever.retrieve()
            when (serverList) {
                is Either.Left -> {
                    serverRepository.setCurrentSelectedServer(
                        CurrentServerState.OnError(
                            serverList.value.message ?: ""
                        )
                    )
                }

                is Either.Right -> {
                    serverRepository.persistExistingServer(serverList.value)
                }
            }
        }
    }

    override fun selectServer(server: AvailableServer) {
        serverRepository.setCurrentSelectedServer(CurrentServerState.OnReady(server))
    }
}