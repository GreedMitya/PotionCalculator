package com.greedmitya.albcalculator.model

import Ingredient



val potionIngredientsByTierAndEnchant: Map<String, Map<String, Map<Int, List<Ingredient>>>> = mapOf(
    // Healing Potion
    "POTION_HEAL" to mapOf(
        "T2" to mapOf(
            0 to listOf(Ingredient("T2_ARCANE_AGARIC", 8)),
            //1 to listOf(Ingredient("T2_ARCANE_AGARIC", 8) )
        ),
        "T4" to mapOf(
            0 to listOf(Ingredient("T4_CRENELLATED_BURDOCK", 24), Ingredient("T3_CHICKEN_EGG", 6)),
            1 to listOf(Ingredient("T4_CRENELLATED_BURDOCK", 24), Ingredient("T3_CHICKEN_EGG", 6), Ingredient("T4_ARCANE_ESSENCE", 5)),
            2 to listOf(Ingredient("T4_CRENELLATED_BURDOCK", 24), Ingredient("T3_CHICKEN_EGG", 6), Ingredient("T6_ARCANE_ESSENCE", 5)),
            3 to listOf(Ingredient("T4_CRENELLATED_BURDOCK", 24), Ingredient("T3_CHICKEN_EGG", 6), Ingredient("T8_ARCANE_ESSENCE", 5))
        ),
        "T6" to mapOf(
            0 to listOf(Ingredient("T6_ELUSIVE_FOXGLOVE", 72), Ingredient("T5_GOOSE_EGG", 18), Ingredient("T6_POTATO_SCHNAPPS", 18)),
            1 to listOf(Ingredient("T6_ELUSIVE_FOXGLOVE", 72), Ingredient("T5_GOOSE_EGG", 18), Ingredient("T6_POTATO_SCHNAPPS", 18), Ingredient("T6_ARCANE_ESSENCE", 5)),
            2 to listOf(Ingredient("T6_ELUSIVE_FOXGLOVE", 72), Ingredient("T5_GOOSE_EGG", 18), Ingredient("T6_POTATO_SCHNAPPS", 18), Ingredient("T8_ARCANE_ESSENCE", 5)),
            3 to listOf(Ingredient("T6_ELUSIVE_FOXGLOVE", 72), Ingredient("T5_GOOSE_EGG", 18), Ingredient("T6_POTATO_SCHNAPPS", 18), Ingredient("T8_ARCANE_ESSENCE", 10))
        )
    ),

    // Energy Potion
    "POTION_ENERGY" to mapOf(
        "T2" to mapOf(
            0 to listOf(Ingredient("T2_ARCANE_AGARIC", 8))
        ),
        "T4" to mapOf(
            0 to listOf(Ingredient("T4_CRENELLATED_BURDOCK", 24), Ingredient("T3_GOAT_MILK", 6)),
            1 to listOf(Ingredient("T4_CRENELLATED_BURDOCK", 24), Ingredient("T3_GOAT_MILK", 6), Ingredient("T4_ARCANE_ESSENCE", 5)),
            2 to listOf(Ingredient("T4_CRENELLATED_BURDOCK", 24), Ingredient("T3_GOAT_MILK", 6), Ingredient("T6_ARCANE_ESSENCE", 5)),
            3 to listOf(Ingredient("T4_CRENELLATED_BURDOCK", 24), Ingredient("T3_GOAT_MILK", 6), Ingredient("T8_ARCANE_ESSENCE", 5))
        ),
        "T6" to mapOf(
            0 to listOf(Ingredient("T6_ELUSIVE_FOXGLOVE", 72), Ingredient("T4_SHEEP_MILK", 18), Ingredient("T6_POTATO_SCHNAPPS", 18)),
            1 to listOf(Ingredient("T6_ELUSIVE_FOXGLOVE", 72), Ingredient("T4_SHEEP_MILK", 18), Ingredient("T6_POTATO_SCHNAPPS", 18), Ingredient("T6_ARCANE_ESSENCE", 5)),
            2 to listOf(Ingredient("T6_ELUSIVE_FOXGLOVE", 72), Ingredient("T4_SHEEP_MILK", 18), Ingredient("T6_POTATO_SCHNAPPS", 18), Ingredient("T8_ARCANE_ESSENCE", 5)),
            3 to listOf(Ingredient("T6_ELUSIVE_FOXGLOVE", 72), Ingredient("T4_SHEEP_MILK", 18), Ingredient("T6_POTATO_SCHNAPPS", 18), Ingredient("T8_ARCANE_ESSENCE", 10))
        )
    ),

    // Gigantify Potion
    "POTION_REVIVE" to mapOf(  // 'REVIVE' is the internal code for Gigantify
        "T3" to mapOf(
            0 to listOf(Ingredient("T3_BRIGHTLEAF_COMFREY", 8))
        ),
        "T5" to mapOf(
            0 to listOf(Ingredient("T5_DRAGON_TEASEL", 24), Ingredient("T4_CRENELLATED_BURDOCK", 12), Ingredient("T5_GOOSE_EGG", 6)),
            1 to listOf(Ingredient("T5_DRAGON_TEASEL", 24), Ingredient("T4_CRENELLATED_BURDOCK", 12), Ingredient("T5_GOOSE_EGG", 6), Ingredient("T4_LIFE_ESSENCE", 5)),
            2 to listOf(Ingredient("T5_DRAGON_TEASEL", 24), Ingredient("T4_CRENELLATED_BURDOCK", 12), Ingredient("T5_GOOSE_EGG", 6), Ingredient("T6_LIFE_ESSENCE", 5)),
            3 to listOf(Ingredient("T5_DRAGON_TEASEL", 24), Ingredient("T4_CRENELLATED_BURDOCK", 12), Ingredient("T5_GOOSE_EGG", 6), Ingredient("T8_LIFE_ESSENCE", 5))
        ),
        "T7" to mapOf(
            0 to listOf(Ingredient("T7_FIRETOUCHED_MULLEIN", 72), Ingredient("T6_ELUSIVE_FOXGLOVE", 36), Ingredient("T4_CRENELLATED_BURDOCK", 36), Ingredient("T5_GOOSE_EGG", 18), Ingredient("T7_CORN_HOOCH", 18)),
            1 to listOf(Ingredient("T7_FIRETOUCHED_MULLEIN", 72), Ingredient("T6_ELUSIVE_FOXGLOVE", 36), Ingredient("T4_CRENELLATED_BURDOCK", 36), Ingredient("T5_GOOSE_EGG", 18), Ingredient("T7_CORN_HOOCH", 18), Ingredient("T6_LIFE_ESSENCE", 5)),
            2 to listOf(Ingredient("T7_FIRETOUCHED_MULLEIN", 72), Ingredient("T6_ELUSIVE_FOXGLOVE", 36), Ingredient("T4_CRENELLATED_BURDOCK", 36), Ingredient("T5_GOOSE_EGG", 18), Ingredient("T7_CORN_HOOCH", 18), Ingredient("T8_LIFE_ESSENCE", 5)),
            3 to listOf(Ingredient("T7_FIRETOUCHED_MULLEIN", 72), Ingredient("T6_ELUSIVE_FOXGLOVE", 36), Ingredient("T4_CRENELLATED_BURDOCK", 36), Ingredient("T5_GOOSE_EGG", 18), Ingredient("T7_CORN_HOOCH", 18), Ingredient("T8_LIFE_ESSENCE", 10))
        )
    ),

    // Resistance Potion
    "POTION_STONESKIN" to mapOf(
        "T3" to mapOf(
            0 to listOf(Ingredient("T3_BRIGHTLEAF_COMFREY", 8))
        ),
        "T5" to mapOf(
            0 to listOf(Ingredient("T5_DRAGON_TEASEL", 24), Ingredient("T4_CRENELLATED_BURDOCK", 12), Ingredient("T3_GOAT_MILK", 6)),
            1 to listOf(Ingredient("T5_DRAGON_TEASEL", 24), Ingredient("T4_CRENELLATED_BURDOCK", 12), Ingredient("T3_GOAT_MILK", 6), Ingredient("T4_LIFE_ESSENCE", 5)),
            2 to listOf(Ingredient("T5_DRAGON_TEASEL", 24), Ingredient("T4_CRENELLATED_BURDOCK", 12), Ingredient("T3_GOAT_MILK", 6), Ingredient("T6_LIFE_ESSENCE", 5)),
            3 to listOf(Ingredient("T5_DRAGON_TEASEL", 24), Ingredient("T4_CRENELLATED_BURDOCK", 12), Ingredient("T3_GOAT_MILK", 6), Ingredient("T8_LIFE_ESSENCE", 5))
        ),
        "T7" to mapOf(
            0 to listOf(Ingredient("T7_FIRETOUCHED_MULLEIN", 72), Ingredient("T6_ELUSIVE_FOXGLOVE", 36), Ingredient("T4_CRENELLATED_BURDOCK", 36), Ingredient("T4_SHEEP_MILK", 18), Ingredient("T7_CORN_HOOCH", 18)),
            1 to listOf(Ingredient("T7_FIRETOUCHED_MULLEIN", 72), Ingredient("T6_ELUSIVE_FOXGLOVE", 36), Ingredient("T4_CRENELLATED_BURDOCK", 36), Ingredient("T4_SHEEP_MILK", 18), Ingredient("T7_CORN_HOOCH", 18), Ingredient("T6_LIFE_ESSENCE", 5)),
            2 to listOf(Ingredient("T7_FIRETOUCHED_MULLEIN", 72), Ingredient("T6_ELUSIVE_FOXGLOVE", 36), Ingredient("T4_CRENELLATED_BURDOCK", 36), Ingredient("T4_SHEEP_MILK", 18), Ingredient("T7_CORN_HOOCH", 18), Ingredient("T8_LIFE_ESSENCE", 5)),
            3 to listOf(Ingredient("T7_FIRETOUCHED_MULLEIN", 72), Ingredient("T6_ELUSIVE_FOXGLOVE", 36), Ingredient("T4_CRENELLATED_BURDOCK", 36), Ingredient("T4_SHEEP_MILK", 18), Ingredient("T7_CORN_HOOCH", 18), Ingredient("T8_LIFE_ESSENCE", 10))
        )
    ),

    // Sticky Potion
    "POTION_SLOWFIELD" to mapOf(
        "T3" to mapOf(
            0 to listOf(Ingredient("T3_BRIGHTLEAF_COMFREY", 8))
        ),
        "T5" to mapOf(
            0 to listOf(Ingredient("T5_DRAGON_TEASEL", 24), Ingredient("T4_CRENELLATED_BURDOCK", 12), Ingredient("T5_GOOSE_EGG", 6)),
            1 to listOf(Ingredient("T5_DRAGON_TEASEL", 24), Ingredient("T4_CRENELLATED_BURDOCK", 12), Ingredient("T5_GOOSE_EGG", 6), Ingredient("T4_LIFE_ESSENCE", 5)),
            2 to listOf(Ingredient("T5_DRAGON_TEASEL", 24), Ingredient("T4_CRENELLATED_BURDOCK", 12), Ingredient("T5_GOOSE_EGG", 6), Ingredient("T6_LIFE_ESSENCE", 5)),
            3 to listOf(Ingredient("T5_DRAGON_TEASEL", 24), Ingredient("T4_CRENELLATED_BURDOCK", 12), Ingredient("T5_GOOSE_EGG", 6), Ingredient("T8_LIFE_ESSENCE", 5))
        ),
        "T7" to mapOf(
            0 to listOf(Ingredient("T7_FIRETOUCHED_MULLEIN", 72), Ingredient("T6_ELUSIVE_FOXGLOVE", 36), Ingredient("T4_CRENELLATED_BURDOCK", 36), Ingredient("T5_GOOSE_EGG", 18)),
            1 to listOf(Ingredient("T7_FIRETOUCHED_MULLEIN", 72), Ingredient("T6_ELUSIVE_FOXGLOVE", 36), Ingredient("T4_CRENELLATED_BURDOCK", 36), Ingredient("T5_GOOSE_EGG", 18), Ingredient("T6_LIFE_ESSENCE", 5)),
            2 to listOf(Ingredient("T7_FIRETOUCHED_MULLEIN", 72), Ingredient("T6_ELUSIVE_FOXGLOVE", 36), Ingredient("T4_CRENELLATED_BURDOCK", 36), Ingredient("T5_GOOSE_EGG", 18), Ingredient("T8_LIFE_ESSENCE", 5)),
            3 to listOf(Ingredient("T7_FIRETOUCHED_MULLEIN", 72), Ingredient("T6_ELUSIVE_FOXGLOVE", 36), Ingredient("T4_CRENELLATED_BURDOCK", 36), Ingredient("T5_GOOSE_EGG", 18), Ingredient("T8_LIFE_ESSENCE", 10))
        )
    ),

    // Poison Potion
    "POTION_COOLDOWN" to mapOf(  // 'COOLDOWN' is the internal code for Poison
        "T4" to mapOf(
            0 to listOf(Ingredient("T4_CRENELLATED_BURDOCK", 8), Ingredient("T3_BRIGHTLEAF_COMFREY", 4)),
            1 to listOf(Ingredient("T4_CRENELLATED_BURDOCK", 8), Ingredient("T3_BRIGHTLEAF_COMFREY", 4), Ingredient("T4_ARCANE_ESSENCE", 5))
        ),
        "T6" to mapOf(
            0 to listOf(Ingredient("T6_ELUSIVE_FOXGLOVE", 24), Ingredient("T5_DRAGON_TEASEL", 12), Ingredient("T3_BRIGHTLEAF_COMFREY", 12), Ingredient("T4_SHEEP_MILK", 6)),
            1 to listOf(Ingredient("T6_ELUSIVE_FOXGLOVE", 24), Ingredient("T5_DRAGON_TEASEL", 12), Ingredient("T3_BRIGHTLEAF_COMFREY", 12), Ingredient("T4_SHEEP_MILK", 6), Ingredient("T6_ARCANE_ESSENCE", 5)),
            2 to listOf(Ingredient("T6_ELUSIVE_FOXGLOVE", 24), Ingredient("T5_DRAGON_TEASEL", 12), Ingredient("T3_BRIGHTLEAF_COMFREY", 12), Ingredient("T4_SHEEP_MILK", 6), Ingredient("T8_ARCANE_ESSENCE", 5)),
            3 to listOf(Ingredient("T6_ELUSIVE_FOXGLOVE", 24), Ingredient("T5_DRAGON_TEASEL", 12), Ingredient("T3_BRIGHTLEAF_COMFREY", 12), Ingredient("T4_SHEEP_MILK", 6), Ingredient("T8_ARCANE_ESSENCE", 10))
        ),
        "T8" to mapOf(
            0 to listOf(Ingredient("T8_GHOUL_YARROW", 72), Ingredient("T7_FIRETOUCHED_MULLEIN", 24), Ingredient("T5_DRAGON_TEASEL", 24), Ingredient("T5_COW_MILK", 18), Ingredient("T8_PUMPKIN_MOONSHINE", 18)),
            1 to listOf(Ingredient("T8_GHOUL_YARROW", 72), Ingredient("T7_FIRETOUCHED_MULLEIN", 24), Ingredient("T5_DRAGON_TEASEL", 24), Ingredient("T5_COW_MILK", 18), Ingredient("T8_PUMPKIN_MOONSHINE", 18), Ingredient("T8_ARCANE_ESSENCE", 5)),
            2 to listOf(Ingredient("T8_GHOUL_YARROW", 72), Ingredient("T7_FIRETOUCHED_MULLEIN", 24), Ingredient("T5_DRAGON_TEASEL", 24), Ingredient("T5_COW_MILK", 18), Ingredient("T8_PUMPKIN_MOONSHINE", 18), Ingredient("T8_ARCANE_ESSENCE", 10)),
            3 to listOf(Ingredient("T8_GHOUL_YARROW", 72), Ingredient("T7_FIRETOUCHED_MULLEIN", 24), Ingredient("T5_DRAGON_TEASEL", 24), Ingredient("T5_COW_MILK", 18), Ingredient("T8_PUMPKIN_MOONSHINE", 18), Ingredient("T8_ARCANE_ESSENCE", 15))
        )
    ),

    // Cleansing/Calming Potion
    "POTION_CLEANSE" to mapOf(
        "T3" to mapOf(
            0 to listOf(Ingredient("T3_BRIGHTLEAF_COMFREY", 8))
        ),
        "T5" to mapOf(
            0 to listOf(Ingredient("T5_DRAGON_TEASEL", 24), Ingredient("T4_CRENELLATED_BURDOCK", 12), Ingredient("T3_GOAT_MILK", 6)),
            1 to listOf(Ingredient("T5_DRAGON_TEASEL", 24), Ingredient("T4_CRENELLATED_BURDOCK", 12), Ingredient("T3_GOAT_MILK", 6), Ingredient("T4_ARCANE_ESSENCE", 5)),
            2 to listOf(Ingredient("T5_DRAGON_TEASEL", 24), Ingredient("T4_CRENELLATED_BURDOCK", 12), Ingredient("T3_GOAT_MILK", 6), Ingredient("T6_ARCANE_ESSENCE", 5)),
            3 to listOf(Ingredient("T5_DRAGON_TEASEL", 24), Ingredient("T4_CRENELLATED_BURDOCK", 12), Ingredient("T3_GOAT_MILK", 6), Ingredient("T8_ARCANE_ESSENCE", 5))
        ),
        "T7" to mapOf(
            0 to listOf(Ingredient("T7_FIRETOUCHED_MULLEIN", 72), Ingredient("T6_ELUSIVE_FOXGLOVE", 36), Ingredient("T4_CRENELLATED_BURDOCK", 36), Ingredient("T4_SHEEP_MILK", 18), Ingredient("T7_CORN_HOOCH", 18)),
            1 to listOf(Ingredient("T7_FIRETOUCHED_MULLEIN", 72), Ingredient("T6_ELUSIVE_FOXGLOVE", 36), Ingredient("T4_CRENELLATED_BURDOCK", 36), Ingredient("T4_SHEEP_MILK", 18), Ingredient("T7_CORN_HOOCH", 18), Ingredient("T6_ARCANE_ESSENCE", 5)),
            2 to listOf(Ingredient("T7_FIRETOUCHED_MULLEIN", 72), Ingredient("T6_ELUSIVE_FOXGLOVE", 36), Ingredient("T4_CRENELLATED_BURDOCK", 36), Ingredient("T4_SHEEP_MILK", 18), Ingredient("T7_CORN_HOOCH", 18), Ingredient("T8_ARCANE_ESSENCE", 5)),
            3 to listOf(Ingredient("T7_FIRETOUCHED_MULLEIN", 72), Ingredient("T6_ELUSIVE_FOXGLOVE", 36), Ingredient("T4_CRENELLATED_BURDOCK", 36), Ingredient("T4_SHEEP_MILK", 18), Ingredient("T7_CORN_HOOCH", 18), Ingredient("T8_ARCANE_ESSENCE", 10))
        ),
        // T8 invisibility potion (treated as extension of cleanse/calming line)
        "T8" to mapOf(
            0 to listOf(Ingredient("T8_GHOUL_YARROW", 72), Ingredient("T7_FIRETOUCHED_MULLEIN", 36), Ingredient("T5_DRAGON_TEASEL", 36), Ingredient("T5_COW_MILK", 18), Ingredient("T8_PUMPKIN_MOONSHINE", 18)),
            1 to listOf(Ingredient("T8_GHOUL_YARROW", 72), Ingredient("T7_FIRETOUCHED_MULLEIN", 36), Ingredient("T5_DRAGON_TEASEL", 36), Ingredient("T5_COW_MILK", 18), Ingredient("T8_PUMPKIN_MOONSHINE", 18), Ingredient("T8_ARCANE_ESSENCE", 5)),
            2 to listOf(Ingredient("T8_GHOUL_YARROW", 72), Ingredient("T7_FIRETOUCHED_MULLEIN", 36), Ingredient("T5_DRAGON_TEASEL", 36), Ingredient("T5_COW_MILK", 18), Ingredient("T8_PUMPKIN_MOONSHINE", 18), Ingredient("T8_ARCANE_ESSENCE", 10)),
            3 to listOf(Ingredient("T8_GHOUL_YARROW", 72), Ingredient("T7_FIRETOUCHED_MULLEIN", 36), Ingredient("T5_DRAGON_TEASEL", 36), Ingredient("T5_COW_MILK", 18), Ingredient("T8_PUMPKIN_MOONSHINE", 18), Ingredient("T8_ARCANE_ESSENCE", 15))
        )
    ),

    // Berserk Potion
    "POTION_BERSERK" to mapOf(
        "T4" to mapOf(
            0 to listOf(Ingredient("T4_CRENELLATED_BURDOCK", 16), Ingredient("T4_WEREWOLF_FANGS", 1))
        ),
        "T6" to mapOf(
            0 to listOf(Ingredient("T6_ELUSIVE_FOXGLOVE", 48), Ingredient("T8_GHOUL_YARROW", 24), Ingredient("T6_WEREWOLF_FANGS", 1))
        ),
        "T8" to mapOf(
            0 to listOf(Ingredient("T8_GHOUL_YARROW", 144), Ingredient("T6_ELUSIVE_FOXGLOVE", 72), Ingredient("T8_WEREWOLF_FANGS", 1))
        )
    ),

    // Hellfire Potion
    "POTION_HELLFIRE" to mapOf(
        "T4" to mapOf(
            0 to listOf(Ingredient("T4_CRENELLATED_BURDOCK", 16), Ingredient("T4_DEMON_HORN", 1))
        ),
        "T6" to mapOf(
            0 to listOf(Ingredient("T6_ELUSIVE_FOXGLOVE", 48), Ingredient("T8_GHOUL_YARROW", 24), Ingredient("T6_DEMON_HORN", 1))
        ),
        "T8" to mapOf(
            0 to listOf(Ingredient("T8_GHOUL_YARROW", 144), Ingredient("T7_FIRETOUCHED_MULLEIN", 72), Ingredient("T8_DEMON_HORN", 1))
        )
    ),

    // Tornado in a Bottle
    "POTION_TORNADO" to mapOf(
        "T4" to mapOf(
            0 to listOf(Ingredient("T4_CRENELLATED_BURDOCK", 16), Ingredient("T4_DAWNBIRD_FEATHER", 1))
        ),
        "T6" to mapOf(
            0 to listOf(Ingredient("T6_ELUSIVE_FOXGLOVE", 48), Ingredient("T8_GHOUL_YARROW", 24), Ingredient("T6_DAWNBIRD_FEATHER", 1))
        ),
        "T8" to mapOf(
            0 to listOf(Ingredient("T8_GHOUL_YARROW", 144), Ingredient("T7_FIRETOUCHED_MULLEIN", 72), Ingredient("T8_DAWNBIRD_FEATHER", 1))
        )
    ),

    // Acid Potion
    "POTION_ACID" to mapOf(
        "T3" to mapOf(
            0 to listOf(Ingredient("T3_BRIGHTLEAF_COMFREY", 8), Ingredient("T4_RUNE", 1))
        ),
        "T5" to mapOf(
            0 to listOf(Ingredient("T5_DRAGON_TEASEL", 48), Ingredient("T4_CRENELLATED_BURDOCK", 24), Ingredient("T6_SOUL", 1))
        ),
        "T7" to mapOf(
            0 to listOf(Ingredient("T7_FIRETOUCHED_MULLEIN", 144), Ingredient("T6_ELUSIVE_FOXGLOVE", 72), Ingredient("T5_DRAGON_TEASEL", 36), Ingredient("T8_RELIC", 1))
        )
    ),

    // Gathering Potion
    "POTION_GATHER" to mapOf(
        "T4" to mapOf(
            0 to listOf(Ingredient("T4_CRENELLATED_BURDOCK", 16), Ingredient("T4_GOLEM_TEETH", 1))
        ),
        "T6" to mapOf(
            0 to listOf(Ingredient("T6_ELUSIVE_FOXGLOVE", 48), Ingredient("T8_GHOUL_YARROW", 24), Ingredient("T6_GOLEM_TEETH", 1))
        ),
        "T8" to mapOf(
            0 to listOf(Ingredient("T8_GHOUL_YARROW", 144), Ingredient("T7_FIRETOUCHED_MULLEIN", 72), Ingredient("T8_GOLEM_TEETH", 1))
        )
    )
)

