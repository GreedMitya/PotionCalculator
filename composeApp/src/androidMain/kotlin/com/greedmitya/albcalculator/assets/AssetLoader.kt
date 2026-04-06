package com.greedmitya.albcalculator.assets

import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import java.io.IOException


@Composable
fun loadAssetImageBitmap(
    id: String,
    folder: String,
    fallback: String = "default.png"
): ImageBitmap {
    val context = LocalContext.current
    val assetManager = context.assets
    val path = "$folder/$id.png"

    return try {
        val stream = assetManager.open(path)
        BitmapFactory.decodeStream(stream).asImageBitmap()
    } catch (e: IOException) {
        val fallbackStream = assetManager.open("$folder/$fallback")
        BitmapFactory.decodeStream(fallbackStream).asImageBitmap()
    }
}


@Composable
fun loadPotionImageBitmapFromDisplayName(
    displayName: String,
    tier: String,
    enchant: Int,
    fallback: String = "default.png"
): ImageBitmap {
    val context = LocalContext.current
    val assetManager = context.assets

    val folder = "potions/$displayName/$tier"
    val fileName = if (enchant == 0) "$tier.png" else "$tier.$enchant.png"
    val path = "$folder/$fileName"

    return try {
        val stream = assetManager.open(path)
        BitmapFactory.decodeStream(stream).asImageBitmap()
    } catch (e: IOException) {
        val fallbackStream = assetManager.open("potions/$fallback")
        BitmapFactory.decodeStream(fallbackStream).asImageBitmap()
    }
}
@Composable
fun loadIngredientImageBitmapById(
    ingredientId: String,
    fallback: String = "default.png"
): ImageBitmap {
    val context = LocalContext.current
    val assetManager = context.assets
    val path = "ingredients/$ingredientId.png"

    return try {
        val stream = assetManager.open(path)
        BitmapFactory.decodeStream(stream).asImageBitmap()
    } catch (e: IOException) {
        val fallbackStream = assetManager.open("ingredients/$fallback")
        BitmapFactory.decodeStream(fallbackStream).asImageBitmap()
    }
}

@Composable
fun getDisplayNameFromItemId(itemId: String): String {
    val lang = LocalConfiguration.current.locales[0].language
    val map = if (lang == "ru") ingredientDisplayNames_ru else ingredientDisplayNames
    return map[itemId] ?: ingredientDisplayNames[itemId] ?: prettifyId(itemId)
}

/** Returns the localized display name for a potion by its baseId (e.g. "POTION_HEAL"). */
@Composable
fun getLocalizedPotionName(baseId: String, englishFallback: String): String {
    val lang = LocalConfiguration.current.locales[0].language
    return if (lang == "ru") potionDisplayNames_ru[baseId] ?: englishFallback else englishFallback
}

/** Returns the localized enchantment label list for the current locale. */
@Composable
fun localizedEnchantments(): List<String> {
    val lang = LocalConfiguration.current.locales[0].language
    return if (lang == "ru") enchantmentLabels_ru else enchantmentLabels_en
}

val ingredientDisplayNames = mapOf(
    // Herbs & food
    "T2_AGARIC" to "Arcane Agaric",
    "T3_EGG" to "Hen Egg",
    "T3_COMFREY" to "Brightleaf Comfrey",
    "T4_BURDOCK" to "Crenellated Burdock",
    "T4_BUTTER" to "Goat's Butter",
    "T4_MILK" to "Goat's Milk",
    "T5_TEASEL" to "Dragon Teasel",
    "T5_EGG" to "Goose Eggs",
    "T6_FOXGLOVE" to "Elusive Foxglove",
    "T6_BUTTER" to "Sheep's Butter",
    "T6_MILK" to "Sheep's Milk",
    "T6_POTATO" to "Potatoes",
    "T6_ALCOHOL" to "Potato Schnapps",
    "T7_MULLEIN" to "Firetouched Mullein",
    "T7_CORN" to "Bundle of Corn",
    "T7_ALCOHOL" to "Corn Hooch",
    "T8_YARROW" to "Ghoul Yarrow",
    "T8_BUTTER" to "Cow's Butter",
    "T8_MILK" to "Cow's Milk",
    "T8_PUMPKIN" to "Pumpkin",
    "T8_ALCOHOL" to "Pumpkin Moonshine",

    // Arcane extracts
    "T1_ALCHEMY_EXTRACT_LEVEL1" to "Basic Arcane Extract",
    "T1_ALCHEMY_EXTRACT_LEVEL2" to "Refined Arcane Extract",
    "T1_ALCHEMY_EXTRACT_LEVEL3" to "Pure Arcane Extract",

    // Rare mob drops — Werewolf
    "T3_ALCHEMY_RARE_WEREWOLF" to "Rugged Werewolf Fangs",
    "T5_ALCHEMY_RARE_WEREWOLF" to "Fine Werewolf Fangs",
    "T7_ALCHEMY_RARE_WEREWOLF" to "Excellent Werewolf Fangs",

    // Rare mob drops — Imp
    "T3_ALCHEMY_RARE_IMP" to "Rugged Imp's Horn",
    "T5_ALCHEMY_RARE_IMP" to "Fine Imp's Horn",
    "T7_ALCHEMY_RARE_IMP" to "Excellent Imp's Horn",

    // Rare mob drops — Elemental
    "T3_ALCHEMY_RARE_ELEMENTAL" to "Rugged Runestone Tooth",
    "T5_ALCHEMY_RARE_ELEMENTAL" to "Fine Runestone Tooth",
    "T7_ALCHEMY_RARE_ELEMENTAL" to "Excellent Runestone Tooth",

    // Rare mob drops — Panther
    "T3_ALCHEMY_RARE_PANTHER" to "Rugged Shadow Claws",
    "T5_ALCHEMY_RARE_PANTHER" to "Fine Shadow Claws",
    "T7_ALCHEMY_RARE_PANTHER" to "Excellent Shadow Claws",

    // Rare mob drops — Ent
    "T3_ALCHEMY_RARE_ENT" to "Rugged Sylvian Root",
    "T5_ALCHEMY_RARE_ENT" to "Fine Sylvian Root",
    "T7_ALCHEMY_RARE_ENT" to "Excellent Sylvian Root",

    // Rare mob drops — Direbear
    "T3_ALCHEMY_RARE_DIREBEAR" to "Rugged Spirit Paws",
    "T5_ALCHEMY_RARE_DIREBEAR" to "Fine Spirit Paws",
    "T7_ALCHEMY_RARE_DIREBEAR" to "Excellent Spirit Paws",

    // Rare mob drops — Eagle
    "T3_ALCHEMY_RARE_EAGLE" to "Rugged Dawnfeather",
    "T5_ALCHEMY_RARE_EAGLE" to "Fine Dawnfeather",
    "T7_ALCHEMY_RARE_EAGLE" to "Excellent Dawnfeather",
)

private fun prettifyId(id: String): String {
    return id
        .replace(Regex("T\\d+_"), "")
        .replace('_', ' ')
        .lowercase()
        .replaceFirstChar { it.uppercaseChar() }
}

// ── Enchantment labels ───────────────────────────────────────────────────────

val enchantmentLabels_en = listOf("Normal (.0)", "Good (.1)", "Outstanding (.2)", "Excellent (.3)")

val enchantmentLabels_ru = listOf("Обычное (.0)", "Хорошее (.1)", "Выдающееся (.2)", "Превосходное (.3)")

// ── Potion display names ─────────────────────────────────────────────────────

/** Russian potion names keyed by baseId (matches CraftViewModel.allPotions baseId field). */
val potionDisplayNames_ru = mapOf(
    "POTION_HEAL"       to "Эликсир здоровья",
    "POTION_ENERGY"     to "Эликсир энергии",
    "POTION_REVIVE"     to "Зелье гиганта",
    "POTION_STONESKIN"  to "Эликсир защиты",
    "POTION_COOLDOWN"   to "Эликсир яда",
    "POTION_CLEANSE"    to "Эликсир невидимости",
    "POTION_SLOWFIELD"  to "Вязкая настойка",
    "ALCOHOL"           to "Алкоголь",
    "POTION_ACID"       to "Кислотное зелье",
    "POTION_BERSERK"    to "Зелье берсерка",
    "POTION_MOB_RESET"  to "Успокаивающее зелье",
    "POTION_CLEANSE2"   to "Очищающее зелье",
    "POTION_LAVA"       to "Зелье адского пламени",
    "POTION_GATHER"     to "Зелье добытчика",
    "POTION_TORNADO"    to "Буря в стакане",
)

// ── Ingredient display names — Russian ───────────────────────────────────────

val ingredientDisplayNames_ru = mapOf(
    // Herbs & food
    "T2_AGARIC"          to "Темногриб",
    "T3_EGG"             to "Куриные яйца",
    "T3_COMFREY"         to "Ярколист",
    "T4_BURDOCK"         to "Зубчатый лопух",
    "T4_BUTTER"          to "Козье масло",
    "T4_MILK"            to "Козье молоко",
    "T5_TEASEL"          to "Драконья ворсянка",
    "T5_EGG"             to "Гусиные яйца",
    "T6_FOXGLOVE"        to "Туманная наперстянка",
    "T6_BUTTER"          to "Овечье масло",
    "T6_MILK"            to "Овечье молоко",
    "T6_POTATO"          to "Картофель",
    "T6_ALCOHOL"         to "Картофельный самогон",
    "T7_MULLEIN"         to "Царский огнецвет",
    "T7_CORN"            to "Кукурузные початки",
    "T7_ALCOHOL"         to "Кукурузный самогон",
    "T8_YARROW"          to "Упырий тысячелистник",
    "T8_BUTTER"          to "Коровье масло",
    "T8_MILK"            to "Коровье молоко",
    "T8_PUMPKIN"         to "Тыква",
    "T8_ALCOHOL"         to "Тыквенный самогон",

    // Arcane extracts
    "T1_ALCHEMY_EXTRACT_LEVEL1" to "Базовый магический экстракт",
    "T1_ALCHEMY_EXTRACT_LEVEL2" to "Очищенный магический экстракт",
    "T1_ALCHEMY_EXTRACT_LEVEL3" to "Чистый магический экстракт",

    // Rare mob drops — Werewolf
    "T3_ALCHEMY_RARE_WEREWOLF" to "Прочные клыки вервольфа",
    "T5_ALCHEMY_RARE_WEREWOLF" to "Отличные клыки вервольфа",
    "T7_ALCHEMY_RARE_WEREWOLF" to "Превосходные клыки вервольфа",

    // Rare mob drops — Imp
    "T3_ALCHEMY_RARE_IMP"      to "Прочный рог чертенка",
    "T5_ALCHEMY_RARE_IMP"      to "Отличный рог чертенка",
    "T7_ALCHEMY_RARE_IMP"      to "Превосходный рог чертенка",

    // Rare mob drops — Elemental
    "T3_ALCHEMY_RARE_ELEMENTAL" to "Прочная зубчатая руна",
    "T5_ALCHEMY_RARE_ELEMENTAL" to "Отличная зубчатая руна",
    "T7_ALCHEMY_RARE_ELEMENTAL" to "Превосходная зубчатая руна",

    // Rare mob drops — Panther
    "T3_ALCHEMY_RARE_PANTHER"  to "Прочные теневые когти",
    "T5_ALCHEMY_RARE_PANTHER"  to "Отличные теневые когти",
    "T7_ALCHEMY_RARE_PANTHER"  to "Превосходные теневые когти",

    // Rare mob drops — Ent
    "T3_ALCHEMY_RARE_ENT"      to "Прочные корни хьерна",
    "T5_ALCHEMY_RARE_ENT"      to "Отличные корни хьерна",
    "T7_ALCHEMY_RARE_ENT"      to "Превосходные корни хьерна",

    // Rare mob drops — Direbear
    "T3_ALCHEMY_RARE_DIREBEAR" to "Прочные лапы духа медведя",
    "T5_ALCHEMY_RARE_DIREBEAR" to "Отличные лапы духа медведя",
    "T7_ALCHEMY_RARE_DIREBEAR" to "Превосходные лапы духа медведя",

    // Rare mob drops — Eagle
    "T3_ALCHEMY_RARE_EAGLE"    to "Прочное перо рассвета",
    "T5_ALCHEMY_RARE_EAGLE"    to "Отличное перо рассвета",
    "T7_ALCHEMY_RARE_EAGLE"    to "Превосходное перо рассвета",
)




