package com.irisvpn.android.androidSpecific

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.irisvpn.android.domain.platform.AdManager
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class GoogleAdsManager(private val context: Context) : AdManager {

    private val adUnitId =
        "ca-app-pub-3940256099942544/5224354917" // Test ID change with it your self

    override fun initialize() {
        MobileAds.initialize(context)
        val requestConfiguration = RequestConfiguration.Builder()
//            .setTestDeviceIds(listOf("YOUR_TEST_DEVICE_ID"))
            .build()
        MobileAds.setRequestConfiguration(requestConfiguration)
    }

    override suspend fun show(): AdState {
        return suspendCancellableCoroutine { continuation ->
            val adRequest = AdRequest.Builder().build()
            RewardedAd.load(context, adUnitId, adRequest, object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            continuation.resume(AdState.Dismissed)
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                           continuation.resume(AdState.FailedToShow)
                        }

                        override fun onAdShowedFullScreenContent() {
                        }
                    }

                    ad.show(context as Activity) { rewardItem ->
                        continuation.resume(AdState.Seen)
                    }
                }
            })
        }
    }
}

sealed interface AdState {
    data object Seen : AdState
    data object FailedToShow : AdState
    data class  FailedToLoad(val message: String) : AdState
    data object Dismissed : AdState
}