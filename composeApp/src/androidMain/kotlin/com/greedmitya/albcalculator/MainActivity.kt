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
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val updateLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { /* flexible update — Play handles download and restart UI */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var showUpdateDialog by mutableStateOf(false)
        val updateChecker = AppUpdateChecker(this)

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
}
