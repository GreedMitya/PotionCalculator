package com.greedmitya.albcalculator

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import com.google.android.play.core.install.InstallStateUpdatedListener
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val updateLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { /* flexible update — download starts in background, listener handles completion */ }

    private lateinit var updateChecker: AppUpdateChecker
    private var installListener: InstallStateUpdatedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initializeAdsWithConsent()

        var showUpdateDialog by mutableStateOf(false)
        updateChecker = AppUpdateChecker(this)

        // When the download finishes in the background — trigger install immediately
        installListener = updateChecker.registerInstallListener {
            lifecycleScope.launch { updateChecker.completeUpdateIfDownloaded() }
        }

        setContent {
            AndroidApp()

            if (showUpdateDialog) {
                UpdateAvailableDialog(
                    whatsNew = WHATS_NEW,
                    onUpdateNow = {
                        showUpdateDialog = false
                        lifecycleScope.launch {
                            updateChecker.startFlexibleUpdate(
                                activity = this@MainActivity,
                                launcher = updateLauncher,
                            )
                        }
                    },
                    onLater = { showUpdateDialog = false },
                )
            }
        }

        lifecycleScope.launch {
            if (updateChecker.isUpdateAvailable()) {
                showUpdateDialog = true
            }
        }
    }

    private fun initializeAdsWithConsent() {
        val params = ConsentRequestParameters.Builder().build()
        val consentInfo = UserMessagingPlatform.getConsentInformation(this)

        consentInfo.requestConsentInfoUpdate(this, params,
            {
                if (consentInfo.isConsentFormAvailable) {
                    UserMessagingPlatform.loadAndShowConsentFormIfRequired(this) {
                        if (consentInfo.canRequestAds()) initMobileAds()
                    }
                } else if (consentInfo.canRequestAds()) {
                    initMobileAds()
                }
            },
            { initMobileAds() }
        )
    }

    private fun initMobileAds() {
        if (BuildConfig.DEBUG) {
            MobileAds.setRequestConfiguration(
                RequestConfiguration.Builder()
                    .setTestDeviceIds(listOf(AdRequest.DEVICE_ID_EMULATOR))
                    .build()
            )
        }
        MobileAds.initialize(this)
    }

    override fun onResume() {
        super.onResume()
        // Handles the case where the app was backgrounded after download completed
        lifecycleScope.launch { updateChecker.completeUpdateIfDownloaded() }
    }

    override fun onDestroy() {
        super.onDestroy()
        installListener?.let { updateChecker.unregisterInstallListener(it) }
    }
}
