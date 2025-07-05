package com.irisvpn.android.androidSpecific

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import com.irisvpn.android.utils.AppPackagesRepository
import com.irisvpn.android.widgets.InstalledApp
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class AndroidInstalledAppRepository(private val context: Context) : AppPackagesRepository {
    override suspend fun getInstalledApp(): List<InstalledApp> = withContext(IO) {
        val packageManager = context.packageManager
        val appInfos = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        } else {
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        }
        return@withContext appInfos
            .map { app ->
                InstalledApp(
                    packageManager.getApplicationLabel(app).toString(),
                    app.packageName,
                    packageManager.getApplicationIcon(app),
                    (app.flags and ApplicationInfo.FLAG_SYSTEM) != 0,
                    false
                )
            }
    }
}