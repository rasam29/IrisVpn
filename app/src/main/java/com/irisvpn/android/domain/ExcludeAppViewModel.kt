package com.irisvpn.android.domain

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irisvpn.android.utils.AppPackagesRepository
import com.irisvpn.android.utils.Preference
import com.irisvpn.android.widgets.InstalledApp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ExcludeAppViewModel(
    private val preference: Preference, private val appPackagesRepository: AppPackagesRepository
) : ViewModel() {

    private val _searchFieldState: MutableStateFlow<String> = MutableStateFlow("")
    val searchFieldState: StateFlow<String> = _searchFieldState.asStateFlow()

    private val _installedApps = MutableStateFlow<List<InstalledApp>>(mutableListOf())

    @OptIn(FlowPreview::class)
    val searchedApps = _searchFieldState.debounce(200).distinctUntilChanged()
        .combine(_installedApps) { queryText, categoriesList ->
            val normalizedQuery = queryText.trim().replace(Regex("\\s+"), " ")
            categoriesList.filter {
                it.packageName.contains(
                    normalizedQuery, true
                ) || it.name.contains(normalizedQuery, true)
            }
        }


    private val _loadingInstalledApp = mutableStateOf(false)
    val loadingInstalledApp: State<Boolean> = _loadingInstalledApp

    fun updateInput(query: String) {
        _searchFieldState.value = query
    }

    fun getAllInstalledApp() {
        viewModelScope.launch {
            _loadingInstalledApp.value = true
            val rowInstalledApp = appPackagesRepository.getInstalledApp()
            val excludedApp = preference.getExcludedApps().parsePackageName()
            val result = rowInstalledApp.sortedBy { it.isSystemApp }
                .map { installedApp -> installedApp.copy(isExcluded = excludedApp.any { it == installedApp.packageName }) }
            _loadingInstalledApp.value = false
            _installedApps.value = result
        }
    }

    fun toggleExcludeSelectedPackageName(packageName: String, isChecked: Boolean) {
        val row = preference.getExcludedApps().parsePackageName().toMutableList()
        if (isChecked) {
            row.add(packageName)
        } else {
            row.remove(packageName)
        }
        preference.saveExcludedApp(row.flattenPackageName())
    }
}

private fun String.parsePackageName(): List<String> = trim().split(",")

private fun List<String>.flattenPackageName(): String = joinToString(separator = ",") { it }
