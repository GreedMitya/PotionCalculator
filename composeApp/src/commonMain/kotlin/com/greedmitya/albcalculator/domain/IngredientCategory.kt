package com.greedmitya.albcalculator.domain

enum class IngredientCategory { HERB, COMPONENT }

/** All 7 crafting cities in the game. */
val ALL_CITIES = listOf(
    "Caerleon",
    "Bridgewatch",
    "Martlock",
    "Lymhurst",
    "Fort Sterling",
    "Thetford",
    "Brecilien",
)

/** Short display names for city columns in the price table. */
val CITY_SHORT_NAMES = mapOf(
    "Caerleon" to "Caer",
    "Bridgewatch" to "Bridge",
    "Martlock" to "Martl",
    "Lymhurst" to "Lymh",
    "Fort Sterling" to "Fort",
    "Thetford" to "Thet",
    "Brecilien" to "Brec",
)

/**
 * Maps each ingredient item ID to its category.
 * Used by the City Price Monitor to split ingredients into sub-tabs.
 */
val INGREDIENT_CATEGORIES: Map<String, IngredientCategory> = mapOf(
    // Herbs (10)
    "T2_AGARIC" to IngredientCategory.HERB,
    "T3_COMFREY" to IngredientCategory.HERB,
    "T4_BURDOCK" to IngredientCategory.HERB,
    "T5_TEASEL" to IngredientCategory.HERB,
    "T6_FOXGLOVE" to IngredientCategory.HERB,
    "T6_POTATO" to IngredientCategory.HERB,
    "T7_CORN" to IngredientCategory.HERB,
    "T7_MULLEIN" to IngredientCategory.HERB,
    "T8_PUMPKIN" to IngredientCategory.HERB,
    "T8_YARROW" to IngredientCategory.HERB,
    // Alchemy extracts (3)
    "T1_ALCHEMY_EXTRACT_LEVEL1" to IngredientCategory.COMPONENT,
    "T1_ALCHEMY_EXTRACT_LEVEL2" to IngredientCategory.COMPONENT,
    "T1_ALCHEMY_EXTRACT_LEVEL3" to IngredientCategory.COMPONENT,
    // Rare animal components — T3 (7)
    "T3_ALCHEMY_RARE_DIREBEAR" to IngredientCategory.COMPONENT,
    "T3_ALCHEMY_RARE_EAGLE" to IngredientCategory.COMPONENT,
    "T3_ALCHEMY_RARE_ELEMENTAL" to IngredientCategory.COMPONENT,
    "T3_ALCHEMY_RARE_ENT" to IngredientCategory.COMPONENT,
    "T3_ALCHEMY_RARE_IMP" to IngredientCategory.COMPONENT,
    "T3_ALCHEMY_RARE_PANTHER" to IngredientCategory.COMPONENT,
    "T3_ALCHEMY_RARE_WEREWOLF" to IngredientCategory.COMPONENT,
    // Rare animal components — T5 (7)
    "T5_ALCHEMY_RARE_DIREBEAR" to IngredientCategory.COMPONENT,
    "T5_ALCHEMY_RARE_EAGLE" to IngredientCategory.COMPONENT,
    "T5_ALCHEMY_RARE_ELEMENTAL" to IngredientCategory.COMPONENT,
    "T5_ALCHEMY_RARE_ENT" to IngredientCategory.COMPONENT,
    "T5_ALCHEMY_RARE_IMP" to IngredientCategory.COMPONENT,
    "T5_ALCHEMY_RARE_PANTHER" to IngredientCategory.COMPONENT,
    "T5_ALCHEMY_RARE_WEREWOLF" to IngredientCategory.COMPONENT,
    // Rare animal components — T7 (7)
    "T7_ALCHEMY_RARE_DIREBEAR" to IngredientCategory.COMPONENT,
    "T7_ALCHEMY_RARE_EAGLE" to IngredientCategory.COMPONENT,
    "T7_ALCHEMY_RARE_ELEMENTAL" to IngredientCategory.COMPONENT,
    "T7_ALCHEMY_RARE_ENT" to IngredientCategory.COMPONENT,
    "T7_ALCHEMY_RARE_IMP" to IngredientCategory.COMPONENT,
    "T7_ALCHEMY_RARE_PANTHER" to IngredientCategory.COMPONENT,
    "T7_ALCHEMY_RARE_WEREWOLF" to IngredientCategory.COMPONENT,
    // Animal products (11)
    "T3_EGG" to IngredientCategory.COMPONENT,
    "T5_EGG" to IngredientCategory.COMPONENT,
    "T4_BUTTER" to IngredientCategory.COMPONENT,
    "T6_BUTTER" to IngredientCategory.COMPONENT,
    "T8_BUTTER" to IngredientCategory.COMPONENT,
    "T4_MILK" to IngredientCategory.COMPONENT,
    "T6_MILK" to IngredientCategory.COMPONENT,
    "T8_MILK" to IngredientCategory.COMPONENT,
    "T6_ALCOHOL" to IngredientCategory.COMPONENT,
    "T7_ALCOHOL" to IngredientCategory.COMPONENT,
    "T8_ALCOHOL" to IngredientCategory.COMPONENT,
)

/** Human-readable display names for ingredient item IDs. */
val INGREDIENT_DISPLAY_NAMES: Map<String, String> = mapOf(
    "T2_AGARIC" to "T2 Agaric",
    "T3_COMFREY" to "T3 Comfrey",
    "T4_BURDOCK" to "T4 Burdock",
    "T5_TEASEL" to "T5 Teasel",
    "T6_FOXGLOVE" to "T6 Foxglove",
    "T6_POTATO" to "T6 Potato",
    "T7_CORN" to "T7 Corn",
    "T7_MULLEIN" to "T7 Mullein",
    "T8_PUMPKIN" to "T8 Pumpkin",
    "T8_YARROW" to "T8 Yarrow",
    "T1_ALCHEMY_EXTRACT_LEVEL1" to "Alchemy Extract L1",
    "T1_ALCHEMY_EXTRACT_LEVEL2" to "Alchemy Extract L2",
    "T1_ALCHEMY_EXTRACT_LEVEL3" to "Alchemy Extract L3",
    "T3_ALCHEMY_RARE_DIREBEAR" to "T3 Dire Bear",
    "T3_ALCHEMY_RARE_EAGLE" to "T3 Eagle",
    "T3_ALCHEMY_RARE_ELEMENTAL" to "T3 Elemental",
    "T3_ALCHEMY_RARE_ENT" to "T3 Ent",
    "T3_ALCHEMY_RARE_IMP" to "T3 Imp",
    "T3_ALCHEMY_RARE_PANTHER" to "T3 Panther",
    "T3_ALCHEMY_RARE_WEREWOLF" to "T3 Werewolf",
    "T5_ALCHEMY_RARE_DIREBEAR" to "T5 Dire Bear",
    "T5_ALCHEMY_RARE_EAGLE" to "T5 Eagle",
    "T5_ALCHEMY_RARE_ELEMENTAL" to "T5 Elemental",
    "T5_ALCHEMY_RARE_ENT" to "T5 Ent",
    "T5_ALCHEMY_RARE_IMP" to "T5 Imp",
    "T5_ALCHEMY_RARE_PANTHER" to "T5 Panther",
    "T5_ALCHEMY_RARE_WEREWOLF" to "T5 Werewolf",
    "T7_ALCHEMY_RARE_DIREBEAR" to "T7 Dire Bear",
    "T7_ALCHEMY_RARE_EAGLE" to "T7 Eagle",
    "T7_ALCHEMY_RARE_ELEMENTAL" to "T7 Elemental",
    "T7_ALCHEMY_RARE_ENT" to "T7 Ent",
    "T7_ALCHEMY_RARE_IMP" to "T7 Imp",
    "T7_ALCHEMY_RARE_PANTHER" to "T7 Panther",
    "T7_ALCHEMY_RARE_WEREWOLF" to "T7 Werewolf",
    "T3_EGG" to "T3 Egg",
    "T5_EGG" to "T5 Egg",
    "T4_BUTTER" to "T4 Butter",
    "T6_BUTTER" to "T6 Butter",
    "T8_BUTTER" to "T8 Butter",
    "T4_MILK" to "T4 Milk",
    "T6_MILK" to "T6 Milk",
    "T8_MILK" to "T8 Milk",
    "T6_ALCOHOL" to "T6 Alcohol",
    "T7_ALCOHOL" to "T7 Alcohol",
    "T8_ALCOHOL" to "T8 Alcohol",
)

/** Item IDs for herbs sub-tab. */
val HERB_ITEM_IDS: List<String> =
    INGREDIENT_CATEGORIES.filter { it.value == IngredientCategory.HERB }.keys.toList().sorted()

/** Item IDs for components sub-tab. */
val COMPONENT_ITEM_IDS: List<String> =
    INGREDIENT_CATEGORIES.filter { it.value == IngredientCategory.COMPONENT }.keys.toList().sorted()
