package com.irisvpn.android.domain

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.irisvpn.android.utils.Preference

class SettingViewModel(private val preference: Preference) : ViewModel() {

    private val _isChecked = mutableStateOf(preference.getAutoConnect())
    val isChecked: State<Boolean> = _isChecked

    fun onCheckedChange(checked: Boolean) {
        _isChecked.value = checked
        preference.toggleAutoConnect(checked)
    }
}