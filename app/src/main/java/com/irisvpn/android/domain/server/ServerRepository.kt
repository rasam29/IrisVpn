package com.irisvpn.android.domain.server

import com.irisvpn.android.domain.core.CurrentServerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ServerRepository {
    fun persistExistingServer(servers: List<AvailableServer>)

    fun getAllAvailableServer(): Flow<List<AvailableServer>>


    fun setCurrentSelectedServer(state: CurrentServerState)

    fun getCurrentSelectedServer(
        scope: CoroutineScope,
        autoSelect: Boolean
    ): StateFlow<CurrentServerState>
}