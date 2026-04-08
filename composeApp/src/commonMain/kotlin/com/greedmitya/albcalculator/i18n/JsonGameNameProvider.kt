package com.greedmitya.albcalculator.i18n

import io.github.aakira.napier.Napier
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import potioncalculator.composeapp.generated.resources.Res

/**
 * Loads game item translations from JSON files bundled in composeResources.
 *
 * Files live at: commonMain/composeResources/files/game_names/{lang_code}.json
 * Falls back to English if:
 *  - The requested language file is missing
 *  - A specific key has no translation
 */
class JsonGameNameProvider : GameNameProvider {

    private val json = Json { ignoreUnknownKeys = true }

    private var englishData: GameNameData = GameNameData()
    private var activeData: GameNameData = GameNameData()

    override var currentLanguage: AppLanguage = AppLanguage.DEFAULT
        private set

    @OptIn(ExperimentalResourceApi::class)
    override suspend fun loadForLanguage(language: AppLanguage) {
        // Always ensure English is loaded as fallback
        if (englishData.ingredients.isEmpty()) {
            englishData = loadFile(AppLanguage.ENGLISH.code) ?: GameNameData()
        }

        val data = if (language == AppLanguage.ENGLISH) {
            englishData
        } else {
            loadFile(language.code) ?: run {
                Napier.w("Game names for '${language.code}' not found, falling back to English")
                englishData
            }
        }

        activeData = data
        currentLanguage = language
    }

    @OptIn(ExperimentalResourceApi::class)
    private suspend fun loadFile(langCode: String): GameNameData? {
        return try {
            val bytes = Res.readBytes("files/game_names/$langCode.json")
            json.decodeFromString(GameNameData.serializer(), bytes.decodeToString())
        } catch (e: Exception) {
            Napier.e("Failed to load game_names/$langCode.json", e)
            null
        }
    }

    override fun getIngredientName(itemId: String): String =
        activeData.ingredients[itemId]
            ?: englishData.ingredients[itemId]
            ?: prettifyId(itemId)

    override fun getPotionDisplayName(englishName: String): String =
        activeData.potions[englishName]
            ?: englishData.potions[englishName]
            ?: englishName

    override fun getCityName(englishName: String): String =
        activeData.cities[englishName]
            ?: englishData.cities[englishName]
            ?: englishName

    /** Last-resort fallback: converts "T4_BURDOCK" → "T4 Burdock". */
    private fun prettifyId(id: String): String =
        id.replace('_', ' ')
            .lowercase()
            .replaceFirstChar { it.uppercaseChar() }
}
