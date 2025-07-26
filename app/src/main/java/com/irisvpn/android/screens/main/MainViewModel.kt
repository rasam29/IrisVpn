package com.irisvpn.android.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irisvpn.android.androidSpecific.AdState
import com.irisvpn.android.domain.core.IrisUseCase
import com.irisvpn.android.domain.server.AvailableServer

class MainViewModel(private val irisUseCase: IrisUseCase): ViewModel() {

    val connectionStats = irisUseCase.connectionStats

    val generalState = irisUseCase.generalState

    val currentServer = irisUseCase.currentIrisServer(viewModelScope)

    val allAvailableServer = irisUseCase.allAvailableServer

    init {
        retryRefreshingServer()
    }

    fun retryRefreshingServer(){
        irisUseCase.refreshServerList(viewModelScope)
    }

    fun toggleConnection(){
        irisUseCase.toggleConnection(viewModelScope)
    }

    fun selectServer(server: AvailableServer){
        irisUseCase.selectServer(server)
    }

    fun adFinished(state: AdState){
        irisUseCase.adFinished(state)
    }
}