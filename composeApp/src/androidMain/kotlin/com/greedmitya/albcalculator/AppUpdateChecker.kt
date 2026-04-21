package com.greedmitya.albcalculator

import android.app.Activity
import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.tasks.Task
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine

// Edit this list before every release — displayed in the update dialog
val WHATS_NEW = listOf(
    "Improved UI across all languages",
    "Focus calculator refinements",
    "Bug fixes and performance improvements",
)

private suspend fun <T> Task<T>.await(): T = suspendCancellableCoroutine { cont ->
    addOnSuccessListener { cont.resume(it) }
    addOnFailureListener { cont.resumeWithException(it) }
}

class AppUpdateChecker(context: Context) {
    private val manager = AppUpdateManagerFactory.create(context)

    suspend fun isUpdateAvailable(): Boolean = try {
        val info = manager.appUpdateInfo.await()
        info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            && info.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
    } catch (_: Exception) {
        false
    }

    suspend fun startFlexibleUpdate(
        activity: Activity,
        launcher: ActivityResultLauncher<IntentSenderRequest>,
    ) {
        try {
            val info = manager.appUpdateInfo.await()
            manager.startUpdateFlowForResult(
                info,
                launcher,
                AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build(),
            )
        } catch (_: Exception) {
            // Best-effort — silent failure if Play Services unavailable or offline
        }
    }
}
