package com.irisvpn.android.screens.setting

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irisvpn.android.domain.core.CurrentServerState
import com.irisvpn.android.domain.platform.Preference
import com.irisvpn.android.domain.server.ServerRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SettingViewModel(
    private val preference: Preference,
    private val serverRepository: ServerRepository
) : ViewModel() {

    private val _isChecked = mutableStateOf(preference.getAutoConnect())
    val isChecked: State<Boolean> = _isChecked


    private val _notFetchedError = MutableSharedFlow<Unit>()
    val notFetchedError: SharedFlow<Unit> = _notFetchedError

    fun onCheckedChange(checked: Boolean) {
        viewModelScope.launch {
            _isChecked.value = checked
            preference.toggleAutoConnect(checked)
            if (checked) {
                val current = serverRepository.getCurrentSelectedServer(viewModelScope, false).value
                if (current !is CurrentServerState.OnReady) {
                    _isChecked.value = false
                    _notFetchedError.emit(Unit)
                }
            }
        }
    }
}