package com.greedmitya.albcalculator.assets

import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import java.io.IOException

private val ingredientNameMap = mapOf(
    "ARCANE_AGARIC" to "Arcane Agaric",
    "BASIC_ARCANE_EXTRACT" to "Basic Arcane Extract",
    "BEAR_ICE_CREAM" to "Bear Ice Cream",
    "BLAZING_BONES" to "Blazing Bones",
    "BRIGHTLEAF_COMFREY" to "Brightleaf Comfrey",
    "BURNING_EMBER" to "Burning Ember",
    "CHOPPED_FISH" to "Chopped Fish",
    "COMMON_ARCANE_EXTRACT" to "Common Arcane Extract",
    "CORN_HOOCH" to "Corn Hooch",
    "COWS_BUTTER" to "Cow's Butter",
    "CRUSHED_ARCTIC" to "Crushed Arctic",
    "CRUSHED_ROCKROOT" to "Crushed Rockroot",
    "CRUSHED_SUNFLARE" to "Crushed Sunflare",
    "CURSED_JAWBONE" to "Cursed Jawbone",
    "DEMONIC_SHARD" to "Demonic Shard",
    "DRAGON_TEETH" to "Dragon Teeth",
    "ELDERFLOWER_SYRUP" to "Elderflower Syrup",
    "ELVISH_NECTAR" to "Elvish Nectar",
    "ENCHANTED_MANDRAKE_ROOT" to "Enchanted Mandrake Root",
    "EXCELLENT_RUNESTONE_TOOTH" to "Excellent Runestone Tooth",
    "EXCEPTIONAL_RUNESTONE_TOOTH" to "Exceptional Runestone Tooth",
    "EXPLOSIVE_MUSHROOM" to "Explosive Mushroom",
    "EYE_OF_GIANT" to "Eye of Giant",
    "FERMENTED_SPIRIT" to "Fermented Spirit",
    "FIERY_BERRIES" to "Fiery Berries",
    "FINE_SYLVIAN_ROOT" to "Fine Sylvian Root",
    "FROST_FLOWERS" to "Frost Flowers",
    "GLOWING_MOSS" to "Glowing Moss",
    "GOATS_MILK" to "Goat's Milk",
    "GOBLIN_NOSE_RING" to "Goblin Nose Ring",
    "GOBLIN_TEETH" to "Goblin Teeth",
    "GREAT_ARCANE_EXTRACT" to "Great Arcane Extract",
    "GREMLIN_TOOTH" to "Gremlin Tooth",
    "GRIFFIN_TALON" to "Griffin Talon",
    "HOLY_ORCHID" to "Holy Orchid",
    "LARGE_ARCANE_EXTRACT" to "Large Arcane Extract",
    "MOUNTAIN_BLOOD" to "Mountain Blood",
    "RARE_ARCANE_EXTRACT" to "Rare Arcane Extract",
    "RUGGED_WEREWOLF_FANGS" to "Rugged Werewolf Fangs",
    "SILVERWEED_ROOT" to "Silverweed Root",
    "SLIPPERY_EEL" to "Slippery Eel",
    "UNICORN_HAIR" to "Unicorn Hair"
)

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
fun loadIngredientImageBitmapByUniqueName(
    uniqueName: String,
    fallback: String = "default.png"
): ImageBitmap {
    val context = LocalContext.current
    val assetManager = context.assets

    // Убираем всё до первого "_" — получаем чистое ключ-имя
    val key = uniqueName.substringAfter("_")

    // Ищем в мапе наше displayName
    val displayName = ingredientNameMap[key] ?: return loadAssetImageBitmap("default", "ingredients", fallback)

    return try {
        val stream = assetManager.open("ingredients/$displayName.png")
        BitmapFactory.decodeStream(stream).asImageBitmap()
    } catch (e: IOException) {
        val fallbackStream = assetManager.open("ingredients/$fallback")
        BitmapFactory.decodeStream(fallbackStream).asImageBitmap()
    }
}
// В AssetLoader.kt или в любом утильном объекте
@Composable
// AssetLoader.kt
fun getDisplayNameFromFileName(uniqueName: String): String {
    return uniqueName
        .split('_')
        // Убираем все части вида T<number>
        .filterNot { it.matches(Regex("T\\d+")) }
        // Приводим каждую оставшуюся часть к Title Case
        .joinToString(" ") { part ->
            part.lowercase().replaceFirstChar { it.uppercaseChar() }
        }
}


