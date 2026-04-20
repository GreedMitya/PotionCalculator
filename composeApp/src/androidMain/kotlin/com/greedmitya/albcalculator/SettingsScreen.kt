package com.greedmitya.albcalculator

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import com.greedmitya.albcalculator.BuildConfig
import com.greedmitya.albcalculator.components.ActionTextButton
import com.greedmitya.albcalculator.components.AppColors
import com.greedmitya.albcalculator.components.SelectorBlock
import com.greedmitya.albcalculator.i18n.AppLanguage
import com.greedmitya.albcalculator.i18n.LocalGameNameProvider
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.jetbrains.compose.resources.stringResource
import potioncalculator.composeapp.generated.resources.*

// Debug colors — defined locally, only compiled into debug builds via the BuildConfig guard
private val DebugRed = Color(0xFFFF4444)
private val DebugGreen = Color(0xFF4CAF50)
private val DebugRedDark = Color(0xFF3E0000)
private val DebugGreenDark = Color(0xFF1B5E20)
private val DebugPanelBg = Color(0xFF1A0000)

@Composable
fun SettingsScreen(viewModel: CraftViewModel, scrollState: ScrollState) {
    val gameNameProvider = LocalGameNameProvider.current
    val languageRepo = koinInject<com.greedmitya.albcalculator.domain.LanguagePreferenceRepository>()
    val scope = rememberCoroutineScope()

    val languageOptions = AppLanguage.available.map { it.code }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BackgroundDark)
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 60.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(Res.string.app_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif,
                color = AppColors.PrimaryGold,
            )
            Text(
                text = stringResource(Res.string.settings_title),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Serif,
                color = AppColors.PrimaryGold,
            )
        }

        Spacer(Modifier.height(24.dp))

        SelectorBlock(
            title = stringResource(Res.string.settings_server_region),
            options = viewModel.serverDisplayNames.values.toList(),
            selectedOption = viewModel.selectedServer,
            onOptionSelected = { viewModel.updateServer(it) },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(4.dp))

        SelectorBlock(
            title = stringResource(Res.string.settings_language),
            options = languageOptions,
            selectedOption = gameNameProvider.currentLanguage.code,
            onOptionSelected = { selectedCode ->
                val language = AppLanguage.fromCode(selectedCode)
                scope.launch {
                    languageRepo.saveLanguage(language)
                    gameNameProvider.loadForLanguage(language)
                    // Update the Android system locale so stringResource() resolves correctly
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(language.bcp47Tag)
                    )
                }
            },
            displayTransform = { code ->
                AppLanguage.fromCode(code).displayName
            },
            modifier = Modifier.fillMaxWidth(),
        )

        // ─── DEBUG PANEL ──────────────────────────────────────────────────────────
        // BuildConfig.DEBUG is false in every release build — this block is never
        // reachable by users. Safe to leave in source forever.
        if (BuildConfig.DEBUG) {
            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = DebugRed.copy(alpha = 0.4f))
            Spacer(Modifier.height(8.dp))

            DebugPremiumPanel(viewModel = viewModel)
        }
    }
}

@Composable
private fun DebugPremiumPanel(viewModel: CraftViewModel) {
    val isUnlocked = viewModel.isAppPremiumUnlocked

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DebugPanelBg, RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = DebugRed, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = "DEBUG TOOLS",
            color = DebugRed,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            letterSpacing = 1.sp,
        )

        Text(
            text = "Premium: ${if (isUnlocked) "✓ UNLOCKED" else "✗ LOCKED"}",
            color = if (isUnlocked) DebugGreen else DebugRed,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
        )

        if (viewModel.premiumPrice != null) {
            Text(
                text = "Price badge: ${viewModel.premiumPrice}",
                color = AppColors.Gray300,
                fontSize = 12.sp,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ActionTextButton(
                text = "Unlock",
                onClick = { viewModel.debugOverridePremiumState(unlocked = true) },
                enabled = !isUnlocked,
                modifier = Modifier.weight(1f),
                backgroundColor = if (isUnlocked) AppColors.Gray500 else DebugGreenDark,
                textColor = if (isUnlocked) AppColors.Gray400 else DebugGreen,
                borderColor = if (isUnlocked) AppColors.Gray400 else DebugGreen,
            )
            ActionTextButton(
                text = "Lock",
                onClick = { viewModel.debugOverridePremiumState(unlocked = false) },
                enabled = isUnlocked,
                modifier = Modifier.weight(1f),
                backgroundColor = if (!isUnlocked) AppColors.Gray500 else DebugRedDark,
                textColor = if (!isUnlocked) AppColors.Gray400 else DebugRed,
                borderColor = if (!isUnlocked) AppColors.Gray400 else DebugRed,
            )
        }

        Text(
            text = "Tap Lock → go to Craft+ or Markets tab to see the upgrade screen.\nNot visible in release builds.",
            color = AppColors.Gray300,
            fontSize = 11.sp,
            lineHeight = 15.sp,
        )
    }
}
