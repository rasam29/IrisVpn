package com.irisvpn.android.domain.server

import com.irisvpn.android.appConfig.SORT_SERVER_INTERVAL
import com.irisvpn.android.domain.core.CurrentServerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class InMemoryServerRepository() : ServerRepository {

    private val _serversFlow = MutableStateFlow<List<AvailableServer>>(emptyList())
    private val _currentSelectedFlow =
        MutableStateFlow<CurrentServerState>(CurrentServerState.Loading)

    init {
        GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                _serversFlow.value = _serversFlow.value.sortedBy {
                    it.ping.value
                }
                delay(SORT_SERVER_INTERVAL)
            }
        }
    }

    override fun persistExistingServer(servers: List<AvailableServer>) {
        _serversFlow.value = servers
        setCurrentSelectedServer(CurrentServerState.OnReady(servers.first()))
    }

    override fun getAllAvailableServer(): Flow<List<AvailableServer>> = _serversFlow

    override fun setCurrentSelectedServer(state: CurrentServerState) {
        _currentSelectedFlow.value = state
    }

    override fun getCurrentSelectedServer(
        scope: CoroutineScope, autoSelect: Boolean
    ): StateFlow<CurrentServerState> =
        if (autoSelect) _serversFlow.map { CurrentServerState.OnReady(it.first()) }.stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = CurrentServerState.Loading
        )
        else {
            _currentSelectedFlow
        }
}