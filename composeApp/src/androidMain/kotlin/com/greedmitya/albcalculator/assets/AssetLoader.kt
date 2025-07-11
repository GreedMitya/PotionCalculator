package com.greedmitya.albcalculator.assets

import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
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
    return ingredientDisplayNames[itemId] ?: prettifyId(itemId)
}

val ingredientDisplayNames = mapOf(
    "T2_AGARIC" to "Arcane Agaric",
    "T3_EGG" to "Hen Egg",
    "T3_COMFREY" to "Brightleaf Comfrey",
    "T4_BURDOCK" to "Crenellated Burdock",
    "T4_MILK" to "Goat's Milk",
    "T5_TEASEL" to "Dragon Teasel",
    "T5_EGG" to "Goose Eggs",
    "T5_MILK" to "Goat's Milk",
    "T6_FOXGLOVE" to "Elusive Foxglove",
    "T6_MILK" to "Sheep's Milk",
    "T6_EGG" to "Goose Eggs",
    "T6_ALCOHOL" to "Potato Schnapps",
    "T7_MULLEIN" to "Firetouched Mullein",
    "T7_ALCOHOL" to "Corn Hooch",
    "T8_YARROW" to "Ghoul Yarrow",
    "T8_MILK" to "Cow's Milk",
    "T8_ALCOHOL" to "Aged Whiskey",

    "T1_ALCHEMY_EXTRACT_LEVEL1" to "Basic Arcane Extract",
    "T1_ALCHEMY_EXTRACT_LEVEL2" to "Refined Arcane Extract",
    "T1_ALCHEMY_EXTRACT_LEVEL3" to "Pure Arcane Extract",

    "T3_ALCHEMY_RARE_WEREWOLF" to "Rugged Werewolf Fangs",
    "T5_ALCHEMY_RARE_WEREWOLF" to "Fine Werewolf Fangs",
    "T7_ALCHEMY_RARE_WEREWOLF" to "Excellent Werewolf Fangs",

    "T3_ALCHEMY_RARE_IMP" to "Rugged Imp's Horn",
    "T5_ALCHEMY_RARE_IMP" to "Fine Imp's Horn",
    "T7_ALCHEMY_RARE_IMP" to "Excellent Imp's Horn",

    "T3_ALCHEMY_RARE_ELEMENTAL" to "Rugged Runestone Tooth",
    "T5_ALCHEMY_RARE_ELEMENTAL" to "Fine Runestone Tooth",
    "T7_ALCHEMY_RARE_ELEMENTAL" to "Excellent Runestone Tooth",

    "T3_ALCHEMY_RARE_PANTHER" to "Rugged Shadow Claws",
    "T5_ALCHEMY_RARE_PANTHER" to "Fine Shadow Claws",
    "T7_ALCHEMY_RARE_PANTHER" to "Excellent Shadow Claws",

    "T3_ALCHEMY_RARE_ENT" to "Rugged Sylvian Root",
    "T5_ALCHEMY_RARE_ENT" to "Fine Sylvian Root",
    "T7_ALCHEMY_RARE_ENT" to "Excellent Sylvian Root",

    "T3_ALCHEMY_RARE_DIREBEAR" to "Rugged Spirit Paws",
    "T5_ALCHEMY_RARE_DIREBEAR" to "Fine Spirit Paws",
    "T7_ALCHEMY_RARE_DIREBEAR" to "Excellent Spirit Paws",

    "T5_ALCHEMY_RARE_EAGLE" to "Fine Dawnfeather",
    "T7_ALCHEMY_RARE_EAGLE" to "Excellent Dawnfeather"

)

private fun prettifyId(id: String): String {
    return id
        .replace(Regex("T\\d+_"), "")
        .replace('_', ' ')
        .lowercase()
        .replaceFirstChar { it.uppercaseChar() }
}




