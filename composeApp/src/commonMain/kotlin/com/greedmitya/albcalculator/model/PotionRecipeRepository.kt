package com.greedmitya.albcalculator.model

import Ingredient



val potionIngredientsByTierAndEnchant: Map<String, Map<String, Map<Int, List<Ingredient>>>> = mapOf(
    "POTION_HEAL" to mapOf(
        // Minor Healing Potion (T2)
        "T2" to mapOf(
            0 to listOf(
                Ingredient("T2_AGARIC", 8)  // Arcane Agaric x8
            ),
            1 to listOf(
                Ingredient("T2_AGARIC", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 5)  // + Basic Arcane Extract x10
            ),
            2 to listOf(
                Ingredient("T2_AGARIC", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 5)  // + Refined Arcane Extract x10
            ),
            3 to listOf(
                Ingredient("T2_AGARIC", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 5)  // + Pure Arcane Extract x10
            )
        ),
        // Healing Potion (T4)
        "T4" to mapOf(
            0 to listOf(
                Ingredient("T4_BURDOCK", 24),    // Crenellated Burdock x24
                Ingredient("T3_EGG", 6)          // Hen Eggs x6
            ),
            1 to listOf(
                Ingredient("T4_BURDOCK", 24),
                Ingredient("T3_EGG", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)  // + Basic Arcane Extract x15
            ),
            2 to listOf(
                Ingredient("T4_BURDOCK", 24),
                Ingredient("T3_EGG", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)  // + Refined Arcane Extract x15
            ),
            3 to listOf(
                Ingredient("T4_BURDOCK", 24),
                Ingredient("T3_EGG", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)  // + Pure Arcane Extract x15
            )
        ),
        // Major Healing Potion (T6)
        "T6" to mapOf(
            0 to listOf(
                Ingredient("T6_FOXGLOVE", 72),    // Elusive Foxglove x72
                Ingredient("T5_EGG", 18),         // Goose Eggs x18
                Ingredient("T6_ALCOHOL", 18)      // Potato Schnapps (алкоголь) x18
            ),
            1 to listOf(
                Ingredient("T6_FOXGLOVE", 72),
                Ingredient("T5_EGG", 18),
                Ingredient("T6_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 45)  // + Basic Arcane Extract x15
            ),
            2 to listOf(
                Ingredient("T6_FOXGLOVE", 72),
                Ingredient("T5_EGG", 18),
                Ingredient("T6_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 45)  // + Refined Arcane Extract x15
            ),
            3 to listOf(
                Ingredient("T6_FOXGLOVE", 72),
                Ingredient("T5_EGG", 18),
                Ingredient("T6_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 45)  // + Pure Arcane Extract x15
            )
        )
    ),
    "POTION_ENERGY" to mapOf(
        // Minor Energy Potion (T2)
        "T2" to mapOf(
            0 to listOf(
                Ingredient("T2_AGARIC", 8)  // Arcane Agaric x8
            ),
            1 to listOf(
                Ingredient("T2_AGARIC", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 5)
            ),
            2 to listOf(
                Ingredient("T2_AGARIC", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 5)
            ),
            3 to listOf(
                Ingredient("T2_AGARIC", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 5)
            )
        ),
        // Energy Potion (T4)
        "T4" to mapOf(
            0 to listOf(
                Ingredient("T4_BURDOCK", 24),   // Crenellated Burdock x24
                Ingredient("T4_MILK", 6)        // Goat's Milk x6
            ),
            1 to listOf(
                Ingredient("T4_BURDOCK", 24),
                Ingredient("T4_MILK", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T4_BURDOCK", 24),
                Ingredient("T4_MILK", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T4_BURDOCK", 24),
                Ingredient("T4_MILK", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        ),
        // Major Energy Potion (T6)
        "T6" to mapOf(
            0 to listOf(
                Ingredient("T6_FOXGLOVE", 72),   // Elusive Foxglove x72
                Ingredient("T6_MILK", 18),       // Sheep's Milk x18
                Ingredient("T6_ALCOHOL", 18)     // Potato Schnapps x18
            ),
            1 to listOf(
                Ingredient("T6_FOXGLOVE", 72),
                Ingredient("T6_MILK", 18),
                Ingredient("T6_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 45)
            ),
            2 to listOf(
                Ingredient("T6_FOXGLOVE", 72),
                Ingredient("T6_MILK", 18),
                Ingredient("T6_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 45)
            ),
            3 to listOf(
                Ingredient("T6_FOXGLOVE", 72),
                Ingredient("T6_MILK", 18),
                Ingredient("T6_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 45)
            )
        )
    ),
    "POTION_REVIVE" to mapOf(
        // Minor Gigantify Potion (T3)
        "T3" to mapOf(
            0 to listOf(
                Ingredient("T3_COMFREY", 8)  // Brightleaf Comfrey x8
            ),
            1 to listOf(
                Ingredient("T3_COMFREY", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 5)
            ),
            2 to listOf(
                Ingredient("T3_COMFREY", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 5)
            ),
            3 to listOf(
                Ingredient("T3_COMFREY", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 5)
            )
        ),
        // Gigantify Potion (T5)
        "T5" to mapOf(
            0 to listOf(
                Ingredient("T5_TEASEL", 24),    // Dragon Teasel x24
                Ingredient("T4_BURDOCK", 12),   // Crenellated Burdock x12
                Ingredient("T5_EGG", 6)         // Goose Eggs x6
            ),
            1 to listOf(
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T5_EGG", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T5_EGG", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T5_EGG", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        ),
        // Major Gigantify Potion (T7)
        "T7" to mapOf(
            0 to listOf(
                Ingredient("T7_MULLEIN", 72),   // Firetouched Mullein x72
                Ingredient("T6_FOXGLOVE", 36),  // Elusive Foxglove x36
                Ingredient("T5_EGG", 18),       //
                Ingredient("T7_ALCOHOL", 18)    // Corn Hooch x18
            ),
            1 to listOf(
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T5_EGG", 18),
                Ingredient("T7_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 45)
            ),
            2 to listOf(
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T7_ALCOHOL", 18),
                Ingredient("T5_EGG", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 45)
            ),
            3 to listOf(
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T7_ALCOHOL", 18),
                Ingredient("T5_EGG", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 45)
            )
        )
    ),
    "POTION_STONESKIN" to mapOf(
        // Minor Resistance Potion (T3)
        "T3" to mapOf(
            0 to listOf(
                Ingredient("T3_COMFREY", 8)  // Brightleaf Comfrey x8
            ),
            1 to listOf(
                Ingredient("T3_COMFREY", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 5)
            ),
            2 to listOf(
                Ingredient("T3_COMFREY", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 5)
            ),
            3 to listOf(
                Ingredient("T3_COMFREY", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 5)
            )
        ),
        // Resistance Potion (T5)
        "T5" to mapOf(
            0 to listOf(
                Ingredient("T5_TEASEL", 24),   // Dragon Teasel x24
                Ingredient("T4_BURDOCK", 12),  // Crenellated Burdock x12
                Ingredient("T4_MILK", 6)       // Goat's Milk x6
            ),
            1 to listOf(
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T4_MILK", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T4_MILK", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T4_MILK", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        ),
        // Major Resistance Potion (T7)
        "T7" to mapOf(
            0 to listOf(
                Ingredient("T7_MULLEIN", 72),   // Firetouched Mullein x72
                Ingredient("T6_FOXGLOVE", 36),  // Elusive Foxglove x36
                Ingredient("T6_MILK", 18),       // Sheep's Milk x18
                Ingredient("T7_ALCOHOL", 18)       // Sheep's Milk x18
            ),
            1 to listOf(
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T6_MILK", 18),
                Ingredient("T7_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 45)
            ),
            2 to listOf(
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T6_MILK", 18),
                Ingredient("T7_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 45)
            ),
            3 to listOf(
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T6_MILK", 18),
                Ingredient("T7_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 45)
            )
        )
    ),
    "POTION_SLOWFIELD" to mapOf(
        // Minor Sticky Potion (T3)
        "T3" to mapOf(
            0 to listOf(
                Ingredient("T3_COMFREY", 8)  // Brightleaf Comfrey x8
            ),
            1 to listOf(
                Ingredient("T3_COMFREY", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 5)
            ),
            2 to listOf(
                Ingredient("T3_COMFREY", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 5)
            ),
            3 to listOf(
                Ingredient("T3_COMFREY", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 5)
            )
        ),
        // Sticky Potion (T5)
        "T5" to mapOf(
            0 to listOf(
                Ingredient("T5_TEASEL", 24),   // Dragon Teasel x24
                Ingredient("T4_BURDOCK", 12),  // Crenellated Burdock x12
                Ingredient("T5_EGG", 6)        // Goose Eggs x6
            ),
            1 to listOf(
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T5_EGG", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T5_EGG", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T5_EGG", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        ),
        // Major Sticky Potion (T7)
        "T7" to mapOf(
            0 to listOf(
                Ingredient("T7_MULLEIN", 72),   // Firetouched Mullein x72
                Ingredient("T6_FOXGLOVE", 36),  // Elusive Foxglove x36
                Ingredient("T4_BURDOCK", 36),
                Ingredient("T5_EGG", 18),
                Ingredient("T7_ALCOHOL", 18),
            ),
            1 to listOf(
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T4_BURDOCK", 36),
                Ingredient("T5_EGG", 18),
                Ingredient("T7_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 45)
            ),
            2 to listOf(
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T4_BURDOCK", 36),
                Ingredient("T5_EGG", 18),
                Ingredient("T7_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 45)
            ),
            3 to listOf(
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T4_BURDOCK", 36),
                Ingredient("T5_EGG", 18),
                Ingredient("T7_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 45)
            )
        )
    ),
    "POTION_COOLDOWN" to mapOf(
        // Minor Poison Potion (T4)
        "T4" to mapOf(
            0 to listOf(
                Ingredient("T4_BURDOCK", 8),    // Crenellated Burdock x8
                Ingredient("T3_COMFREY", 4)     // Brightleaf Comfrey x4
            ),
            1 to listOf(
                Ingredient("T4_BURDOCK", 8),
                Ingredient("T3_COMFREY", 4),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 5)
            ),
            2 to listOf(
                Ingredient("T4_BURDOCK", 8),
                Ingredient("T3_COMFREY", 4),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 5)
            ),
            3 to listOf(
                Ingredient("T4_BURDOCK", 8),
                Ingredient("T3_COMFREY", 4),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 5)
            )
        ),
        // Poison Potion (T6)
        "T6" to mapOf(
            0 to listOf(
                Ingredient("T6_FOXGLOVE", 24),  // Elusive Foxglove x24
                Ingredient("T5_TEASEL", 12),    // Dragon Teasel x12
                Ingredient("T3_COMFREY", 12),   // Brightleaf Comfrey x12
                Ingredient("T6_MILK", 6)        // Sheep's Milk x6
            ),
            1 to listOf(
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12),
                Ingredient("T3_COMFREY", 12),
                Ingredient("T6_MILK", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12),
                Ingredient("T3_COMFREY", 12),
                Ingredient("T6_MILK", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12),
                Ingredient("T3_COMFREY", 12),
                Ingredient("T6_MILK", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        ),
        // Major Poison Potion (T8)
        "T8" to mapOf(
            0 to listOf(
                Ingredient("T8_YARROW", 72),    // Ghoul Yarrow x72
                Ingredient("T7_MULLEIN", 36),   // Firetouched Mullein x36
                Ingredient("T5_TEASEL", 36),
                Ingredient("T8_MILK", 18),
                Ingredient("T8_ALCOHOL", 18),
            ),
            1 to listOf(
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 36),
                Ingredient("T5_TEASEL", 36),
                Ingredient("T8_MILK", 18),
                Ingredient("T8_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 45)
            ),
            2 to listOf(
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 36),
                Ingredient("T5_TEASEL", 36),
                Ingredient("T8_MILK", 18),
                Ingredient("T8_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 45)
            ),
            3 to listOf(
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 36),
                Ingredient("T5_TEASEL", 36),
                Ingredient("T8_MILK", 18),
                Ingredient("T8_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 45)
            )
        )
    ),
    "POTION_BERSERK" to mapOf(
        // Minor Berserk Potion (T4)
        "T4" to mapOf(
            0 to listOf(
                Ingredient("T3_ALCHEMY_RARE_WEREWOLF", 1),  // Rugged Werewolf Fangs x1
                Ingredient("T4_BURDOCK", 16)                // Crenellated Burdock x16
            ),
            1 to listOf(
                Ingredient("T3_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T4_BURDOCK", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T3_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T4_BURDOCK", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T3_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T4_BURDOCK", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        ),
        // Berserk Potion (T6)
        "T6" to mapOf(
            0 to listOf(
                Ingredient("T5_ALCHEMY_RARE_WEREWOLF", 1),  // Fine Werewolf Fangs x1
                Ingredient("T6_FOXGLOVE", 24),              // Elusive Foxglove x24
                Ingredient("T5_TEASEL", 12)                 // Dragon Teasel x12
            ),
            1 to listOf(
                Ingredient("T5_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T5_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T5_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        ),
        // Major Berserk Potion (T8)
        "T8" to mapOf(
            0 to listOf(
                Ingredient("T7_ALCHEMY_RARE_WEREWOLF", 1),  // Excellent Werewolf Fangs x1
                Ingredient("T8_YARROW", 72),                // Ghoul Yarrow x72
                Ingredient("T7_MULLEIN", 36)                // Firetouched Mullein x36
            ),
            1 to listOf(
                Ingredient("T7_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T7_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T7_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        )
    ),
    "POTION_LAVA" to mapOf(
        // Minor Hellfire Potion (T4)
        "T4" to mapOf(
            0 to listOf(
                Ingredient("T3_ALCHEMY_RARE_IMP", 1),   // Rugged Imp's Horn x1
                Ingredient("T4_BURDOCK", 16)            // Crenellated Burdock x16
            ),
            1 to listOf(
                Ingredient("T3_ALCHEMY_RARE_IMP", 1),
                Ingredient("T4_BURDOCK", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T3_ALCHEMY_RARE_IMP", 1),
                Ingredient("T4_BURDOCK", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T3_ALCHEMY_RARE_IMP", 1),
                Ingredient("T4_BURDOCK", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        ),
        // Hellfire Potion (T6)
        "T6" to mapOf(
            0 to listOf(
                Ingredient("T5_ALCHEMY_RARE_IMP", 1),   // Fine Imp's Horn x1
                Ingredient("T6_FOXGLOVE", 24),          // Elusive Foxglove x24
                Ingredient("T5_TEASEL", 12)             // Dragon Teasel x12
            ),
            1 to listOf(
                Ingredient("T5_ALCHEMY_RARE_IMP", 1),
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T5_ALCHEMY_RARE_IMP", 1),
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T5_ALCHEMY_RARE_IMP", 1),
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        ),
        // Major Hellfire Potion (T8)
        "T8" to mapOf(
            0 to listOf(
                Ingredient("T7_ALCHEMY_RARE_IMP", 1),   // Excellent Imp's Horn x1
                Ingredient("T8_YARROW", 72),            // Ghoul Yarrow x72
                Ingredient("T7_MULLEIN", 36)            // Firetouched Mullein x36
            ),
            1 to listOf(
                Ingredient("T7_ALCHEMY_RARE_IMP", 1),
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T7_ALCHEMY_RARE_IMP", 1),
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T7_ALCHEMY_RARE_IMP", 1),
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        )
    ),
    "POTION_GATHER" to mapOf(
        // Minor Gathering Potion (T4)
        "T4" to mapOf(
            0 to listOf(
                Ingredient("T4_BURDOCK", 8),
                Ingredient("T3_COMFREY", 4)
            ),
            1 to listOf(
                Ingredient("T4_BURDOCK", 8),
                Ingredient("T3_COMFREY", 4),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T4_BURDOCK", 8),
                Ingredient("T3_COMFREY", 4),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T4_BURDOCK", 8),
                Ingredient("T3_COMFREY", 4),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        ),
        // Gathering Potion (T6)
        "T6" to mapOf(
            0 to listOf(
                Ingredient("T6_FOXGLOVE", 24),  // Elusive Foxglove x24
                Ingredient("T5_TEASEL", 12),    // Dragon Teasel x12
                Ingredient("T5_EGG", 6)         // Goose Eggs x6
            ),
            1 to listOf(
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12),
                Ingredient("T5_EGG", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12),
                Ingredient("T5_EGG", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12),
                Ingredient("T5_EGG", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        ),
        // Major Gathering Potion (T8)
        "T8" to mapOf(
            0 to listOf(
                Ingredient("T8_YARROW", 72),   // Ghoul Yarrow x72
                Ingredient("T7_MULLEIN", 36),  // Firetouched Mullein x36
                Ingredient("T8_MILK", 6)       // Cow's Milk x6
            ),
            1 to listOf(
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 36),
                Ingredient("T8_MILK", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 36),
                Ingredient("T8_MILK", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 36),
                Ingredient("T8_MILK", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        )
    ),
    "POTION_TORNADO" to mapOf(
        // Minor Tornado in a Bottle (T4)
        "T4" to mapOf(
            0 to listOf(
                Ingredient("T3_ALCHEMY_RARE_ELEMENTAL", 1),  // Rugged Runestone Tooth x1
                Ingredient("T4_BURDOCK", 16)
            ),
            1 to listOf(
                Ingredient("T3_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T4_BURDOCK", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T3_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T4_BURDOCK", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T3_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T4_BURDOCK", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        ),
        // Tornado in a Bottle (T6)
        "T6" to mapOf(
            0 to listOf(
                Ingredient("T5_ALCHEMY_RARE_ELEMENTAL", 1),  // Fine Runestone Tooth x1
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12)
            ),
            1 to listOf(
                Ingredient("T5_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T5_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T5_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        ),
        // Major Tornado in a Bottle (T8)
        "T8" to mapOf(
            0 to listOf(
                Ingredient("T7_ALCHEMY_RARE_ELEMENTAL", 1),  // Excellent Runestone Tooth x1
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 36)
            ),
            1 to listOf(
                Ingredient("T7_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T7_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T7_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        )
    ),
    "POTION_MOB_RESET" to mapOf(
        // Minor Calming Potion (T3)
        "T3" to mapOf(
            0 to listOf(
                Ingredient("T3_ALCHEMY_RARE_PANTHER", 1),  // Rugged Shadow Claws x1
                Ingredient("T3_COMFREY", 8)
            ),
            1 to listOf(
                Ingredient("T3_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T3_COMFREY", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 10)
            ),
            2 to listOf(
                Ingredient("T3_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T3_COMFREY", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 10)
            ),
            3 to listOf(
                Ingredient("T3_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T3_COMFREY", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 10)
            )
        ),
        // Calming Potion (T5)
        "T5" to mapOf(
            0 to listOf(
                Ingredient("T5_ALCHEMY_RARE_PANTHER", 1),   // Fine Shadow Claws x1
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12)
            ),
            1 to listOf(
                Ingredient("T5_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T5_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T5_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        ),
        // Major Calming Potion (T7)
        "T7" to mapOf(
            0 to listOf(
                Ingredient("T7_ALCHEMY_RARE_PANTHER", 1),   // Excellent Shadow Claws x1
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36)
            ),
            1 to listOf(
                Ingredient("T7_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T7_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T7_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        )
    ),
    "POTION_CLEANSE2" to mapOf(
        // Minor Cleansing Potion (T3)
        "T3" to mapOf(
            0 to listOf(
                Ingredient("T3_ALCHEMY_RARE_ENT", 1),  // Rugged Sylvian Root x1
                Ingredient("T3_COMFREY", 8)
            ),
            1 to listOf(
                Ingredient("T3_ALCHEMY_RARE_ENT", 1),
                Ingredient("T3_COMFREY", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 10)
            ),
            2 to listOf(
                Ingredient("T3_ALCHEMY_RARE_ENT", 1),
                Ingredient("T3_COMFREY", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 10)
            ),
            3 to listOf(
                Ingredient("T3_ALCHEMY_RARE_ENT", 1),
                Ingredient("T3_COMFREY", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 10)
            )
        ),
        // Cleansing Potion (T5)
        "T5" to mapOf(
            0 to listOf(
                Ingredient("T5_ALCHEMY_RARE_ENT", 1),   // Fine Sylvian Root x1
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12)
            ),
            1 to listOf(
                Ingredient("T5_ALCHEMY_RARE_ENT", 1),
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T5_ALCHEMY_RARE_ENT", 1),
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T5_ALCHEMY_RARE_ENT", 1),
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        ),
        // Major Cleansing Potion (T7)
        "T7" to mapOf(
            0 to listOf(
                Ingredient("T7_ALCHEMY_RARE_ENT", 1),   // Excellent Sylvian Root x1
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36)
            ),
            1 to listOf(
                Ingredient("T7_ALCHEMY_RARE_ENT", 1),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T7_ALCHEMY_RARE_ENT", 1),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T7_ALCHEMY_RARE_ENT", 1),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        )
    ),
    "POTION_ACID" to mapOf(
        // Minor Acid Potion (T3)
        "T3" to mapOf(
            0 to listOf(
                Ingredient("T3_ALCHEMY_RARE_DIREBEAR", 1),  // Rugged Spirit Paws x1
                Ingredient("T3_COMFREY", 8)
            ),
            1 to listOf(
                Ingredient("T3_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T3_COMFREY", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 10)
            ),
            2 to listOf(
                Ingredient("T3_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T3_COMFREY", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 10)
            ),
            3 to listOf(
                Ingredient("T3_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T3_COMFREY", 8),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 10)
            )
        ),
        // Acid Potion (T5)
        "T5" to mapOf(
            0 to listOf(
                Ingredient("T5_ALCHEMY_RARE_DIREBEAR", 1),  // Fine Spirit Paws x1
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12)
            ),
            1 to listOf(
                Ingredient("T5_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T5_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T5_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        ),
        // Major Acid Potion (T7)
        "T7" to mapOf(
            0 to listOf(
                Ingredient("T7_ALCHEMY_RARE_DIREBEAR", 1),  // Excellent Spirit Paws x1
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36)
            ),
            1 to listOf(
                Ingredient("T7_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T7_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T7_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        )
    ),
    "POTION_CLEANSE" to mapOf(
        // Invisibility Potion (T8)
        "T8" to mapOf(
            0 to listOf(
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 36),
                Ingredient("T5_TEASEL", 36),
                Ingredient("T8_MILK", 18),
                Ingredient("T8_ALCOHOL", 18),
            ),
            1 to listOf(
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 36),
                Ingredient("T5_TEASEL", 36),
                Ingredient("T8_MILK", 18),
                Ingredient("T8_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 45)
            ),
            2 to listOf(
                // (Enchant 2 and 3 также используют Excellent Dawnfeather)
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 36),
                Ingredient("T5_TEASEL", 36),
                Ingredient("T8_MILK", 18),
                Ingredient("T8_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 45)
            ),
            3 to listOf(
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 36),
                Ingredient("T5_TEASEL", 36),
                Ingredient("T8_MILK", 18),
                Ingredient("T8_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 45)
            )
        )
    )
)


