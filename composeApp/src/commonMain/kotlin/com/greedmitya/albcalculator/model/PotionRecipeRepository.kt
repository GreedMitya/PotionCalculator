package com.greedmitya.albcalculator.model

import Ingredient



val potionIngredientsByTierAndEnchant: Map<String, Map<String, Map<Int, List<Ingredient>>>> = mapOf(
    "POTION_HEAL" to mapOf(
        "T2" to mapOf(
            0 to listOf(
                Ingredient("T2_AGARIC", 8)
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
        "T4" to mapOf(
            0 to listOf(
                Ingredient("T4_BURDOCK", 24),
                Ingredient("T3_EGG", 6)
            ),
            1 to listOf(
                Ingredient("T4_BURDOCK", 24),
                Ingredient("T3_EGG", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 15)
            ),
            2 to listOf(
                Ingredient("T4_BURDOCK", 24),
                Ingredient("T3_EGG", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 15)
            ),
            3 to listOf(
                Ingredient("T4_BURDOCK", 24),
                Ingredient("T3_EGG", 6),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 15)
            )
        ),

        "T6" to mapOf(
            0 to listOf(
                Ingredient("T6_FOXGLOVE", 72),
                Ingredient("T5_EGG", 18),
                Ingredient("T6_ALCOHOL", 18)
            ),
            1 to listOf(
                Ingredient("T6_FOXGLOVE", 72),
                Ingredient("T5_EGG", 18),
                Ingredient("T6_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 45)
            ),
            2 to listOf(
                Ingredient("T6_FOXGLOVE", 72),
                Ingredient("T5_EGG", 18),
                Ingredient("T6_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 45)
            ),
            3 to listOf(
                Ingredient("T6_FOXGLOVE", 72),
                Ingredient("T5_EGG", 18),
                Ingredient("T6_ALCOHOL", 18),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 45)
            )
        )
    ),
    "POTION_ENERGY" to mapOf(
        "T2" to mapOf(
            0 to listOf(
                Ingredient("T2_AGARIC", 8)
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
        "T4" to mapOf(
            0 to listOf(
                Ingredient("T4_BURDOCK", 24),
                Ingredient("T4_MILK", 6)
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
        "T6" to mapOf(
            0 to listOf(
                Ingredient("T6_FOXGLOVE", 72),
                Ingredient("T6_MILK", 18),
                Ingredient("T6_ALCOHOL", 18)
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
        "T3" to mapOf(
            0 to listOf(
                Ingredient("T3_COMFREY", 8)
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
        "T5" to mapOf(
            0 to listOf(
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T5_EGG", 6)
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
        "T7" to mapOf(
            0 to listOf(
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T5_EGG", 18),
                Ingredient("T7_ALCOHOL", 18)
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
        "T3" to mapOf(
            0 to listOf(
                Ingredient("T3_COMFREY", 8)
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
        "T5" to mapOf(
            0 to listOf(
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T4_MILK", 6)
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

        "T7" to mapOf(
            0 to listOf(
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T6_MILK", 18),
                Ingredient("T7_ALCOHOL", 18)
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

        "T3" to mapOf(
            0 to listOf(
                Ingredient("T3_COMFREY", 8)
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
        "T5" to mapOf(
            0 to listOf(
                Ingredient("T5_TEASEL", 24),
                Ingredient("T4_BURDOCK", 12),
                Ingredient("T5_EGG", 6)
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

        "T7" to mapOf(
            0 to listOf(
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
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
        "T4" to mapOf(
            0 to listOf(
                Ingredient("T4_BURDOCK", 8),
                Ingredient("T3_COMFREY", 4)
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
        "T6" to mapOf(
            0 to listOf(
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12),
                Ingredient("T3_COMFREY", 12),
                Ingredient("T6_MILK", 6)
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
        "T4" to mapOf(
            0 to listOf(
                Ingredient("T3_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T4_BURDOCK", 16)
            ),
            1 to listOf(
                Ingredient("T3_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T4_BURDOCK", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 10)
            ),
            2 to listOf(
                Ingredient("T3_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T4_BURDOCK", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 10)
            ),
            3 to listOf(
                Ingredient("T3_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T4_BURDOCK", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 10)
            )
        ),
        "T6" to mapOf(
            0 to listOf(
                Ingredient("T5_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T6_FOXGLOVE", 48),
                Ingredient("T2_AGARIC", 24),
                Ingredient("T6_ALCOHOL", 12),
            ),
            1 to listOf(
                Ingredient("T5_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T6_FOXGLOVE", 48),
                Ingredient("T2_AGARIC", 24),
                Ingredient("T6_ALCOHOL", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 30)
            ),
            2 to listOf(
                Ingredient("T5_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T6_FOXGLOVE", 48),
                Ingredient("T2_AGARIC", 24),
                Ingredient("T6_ALCOHOL", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 30)
            ),
            3 to listOf(
                Ingredient("T5_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T6_FOXGLOVE", 48),
                Ingredient("T2_AGARIC", 24),
                Ingredient("T6_ALCOHOL", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 30)
            )
        ),
        "T8" to mapOf(
            0 to listOf(
                Ingredient("T7_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T8_YARROW", 144),
                Ingredient("T3_COMFREY", 72),
                Ingredient("T6_ALCOHOL", 72),
                Ingredient("T7_ALCOHOL", 36),
                Ingredient("T8_ALCOHOL", 36),
            ),
            1 to listOf(
                Ingredient("T7_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T8_YARROW", 144),
                Ingredient("T3_COMFREY", 72),
                Ingredient("T6_ALCOHOL", 72),
                Ingredient("T7_ALCOHOL", 36),
                Ingredient("T8_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 90)
            ),
            2 to listOf(
                Ingredient("T7_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T8_YARROW", 144),
                Ingredient("T3_COMFREY", 72),
                Ingredient("T6_ALCOHOL", 72),
                Ingredient("T7_ALCOHOL", 36),
                Ingredient("T8_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 90)
            ),
            3 to listOf(
                Ingredient("T7_ALCHEMY_RARE_WEREWOLF", 1),
                Ingredient("T8_YARROW", 144),
                Ingredient("T3_COMFREY", 72),
                Ingredient("T6_ALCOHOL", 72),
                Ingredient("T7_ALCOHOL", 36),
                Ingredient("T8_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 90)
            )
        )
    ),
    "POTION_LAVA" to mapOf(
        "T4" to mapOf(
            0 to listOf(
                Ingredient("T3_ALCHEMY_RARE_IMP", 1),
                Ingredient("T4_MILK", 16)
            ),
            1 to listOf(
                Ingredient("T3_ALCHEMY_RARE_IMP", 1),
                Ingredient("T4_MILK", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 10)
            ),
            2 to listOf(
                Ingredient("T3_ALCHEMY_RARE_IMP", 1),
                Ingredient("T4_MILK", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 10)
            ),
            3 to listOf(
                Ingredient("T3_ALCHEMY_RARE_IMP", 1),
                Ingredient("T4_MILK", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 10)
            )
        ),
        "T6" to mapOf(
            0 to listOf(
                Ingredient("T5_ALCHEMY_RARE_IMP", 1),
                Ingredient("T6_MILK", 48),
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T3_EGG", 12),
            ),
            1 to listOf(
                Ingredient("T5_ALCHEMY_RARE_IMP", 1),
                Ingredient("T6_MILK", 48),
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T3_EGG", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 30)
            ),
            2 to listOf(
                Ingredient("T5_ALCHEMY_RARE_IMP", 1),
                Ingredient("T6_MILK", 48),
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T3_EGG", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 30)
            ),
            3 to listOf(
                Ingredient("T5_ALCHEMY_RARE_IMP", 1),
                Ingredient("T6_MILK", 48),
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T3_EGG", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 30)
            )
        ),
        "T8" to mapOf(
            0 to listOf(
                Ingredient("T7_ALCHEMY_RARE_IMP", 1),
                Ingredient("T8_MILK", 144),
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T5_EGG", 36),
                Ingredient("T8_ALCOHOL", 36),
            ),
            1 to listOf(
                Ingredient("T7_ALCHEMY_RARE_IMP", 1),
                Ingredient("T8_MILK", 144),
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T5_EGG", 36),
                Ingredient("T8_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 90)
            ),
            2 to listOf(
                Ingredient("T7_ALCHEMY_RARE_IMP", 1),
                Ingredient("T8_MILK", 144),
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T5_EGG", 36),
                Ingredient("T8_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 90)
            ),
            3 to listOf(
                Ingredient("T7_ALCHEMY_RARE_IMP", 1),
                Ingredient("T8_MILK", 144),
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T5_EGG", 36),
                Ingredient("T8_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 90)
            )
        )
    ),
    "POTION_GATHER" to mapOf(
        "T4" to mapOf(
            0 to listOf(
                Ingredient("T3_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T4_BUTTER", 16),
            ),
            1 to listOf(
                Ingredient("T3_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T4_BUTTER", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 10)
            ),
            2 to listOf(
                Ingredient("T3_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T4_BUTTER", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 10)
            ),
            3 to listOf(
                Ingredient("T3_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T4_BUTTER", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 10)
            )
        ),
        "T6" to mapOf(
            0 to listOf(
                Ingredient("T5_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T6_BUTTER", 48),
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12),
            ),
            1 to listOf(
                Ingredient("T5_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T6_BUTTER", 48),
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 30)
            ),
            2 to listOf(
                Ingredient("T5_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T6_BUTTER", 48),
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 30)
            ),
            3 to listOf(
                Ingredient("T5_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T6_BUTTER", 48),
                Ingredient("T6_FOXGLOVE", 24),
                Ingredient("T5_TEASEL", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 30)
            )
        ),
        "T8" to mapOf(
            0 to listOf(
                Ingredient("T7_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T8_BUTTER", 144),
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T8_ALCOHOL", 36),
            ),
            1 to listOf(
                Ingredient("T7_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T8_BUTTER", 144),
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T8_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 90)
            ),
            2 to listOf(
                Ingredient("T7_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T8_BUTTER", 144),
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T8_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 90)
            ),
            3 to listOf(
                Ingredient("T7_ALCHEMY_RARE_ELEMENTAL", 1),
                Ingredient("T8_BUTTER", 144),
                Ingredient("T8_YARROW", 72),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T6_FOXGLOVE", 36),
                Ingredient("T8_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 90)
            )
        )
    ),
    "POTION_TORNADO" to mapOf(
        "T4" to mapOf(
            0 to listOf(
                Ingredient("T3_ALCHEMY_RARE_EAGLE", 1),
                Ingredient("T4_BURDOCK", 16)
            ),
            1 to listOf(
                Ingredient("T3_ALCHEMY_RARE_EAGLE", 1),
                Ingredient("T4_BURDOCK", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 10)
            ),
            2 to listOf(
                Ingredient("T3_ALCHEMY_RARE_EAGLE", 1),
                Ingredient("T4_BURDOCK", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 10)
            ),
            3 to listOf(
                Ingredient("T3_ALCHEMY_RARE_EAGLE", 1),
                Ingredient("T4_BURDOCK", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 10)
            )
        ),
        "T6" to mapOf(
            0 to listOf(
                Ingredient("T5_ALCHEMY_RARE_EAGLE", 1),
                Ingredient("T6_FOXGLOVE", 48),
                Ingredient("T5_TEASEL", 24),
                Ingredient("T3_EGG", 12),
            ),
            1 to listOf(
                Ingredient("T5_ALCHEMY_RARE_EAGLE", 1),
                Ingredient("T6_FOXGLOVE", 48),
                Ingredient("T5_TEASEL", 24),
                Ingredient("T3_EGG", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 30)
            ),
            2 to listOf(
                Ingredient("T5_ALCHEMY_RARE_EAGLE", 1),
                Ingredient("T6_FOXGLOVE", 48),
                Ingredient("T5_TEASEL", 24),
                Ingredient("T3_EGG", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 30)
            ),
            3 to listOf(
                Ingredient("T5_ALCHEMY_RARE_EAGLE", 1),
                Ingredient("T6_FOXGLOVE", 48),
                Ingredient("T5_TEASEL", 24),
                Ingredient("T3_EGG", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 30)
            )
        ),
        "T8" to mapOf(
            0 to listOf(
                Ingredient("T7_ALCHEMY_RARE_EAGLE", 1),
                Ingredient("T8_YARROW", 144),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T7_ALCOHOL", 72),
                Ingredient("T5_EGG", 36),
                Ingredient("T8_ALCOHOL", 36),
            ),
            1 to listOf(
                Ingredient("T7_ALCHEMY_RARE_EAGLE", 1),
                Ingredient("T8_YARROW", 144),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T7_ALCOHOL", 72),
                Ingredient("T5_EGG", 36),
                Ingredient("T8_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 90)
            ),
            2 to listOf(
                Ingredient("T7_ALCHEMY_RARE_EAGLE", 1),
                Ingredient("T8_YARROW", 144),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T7_ALCOHOL", 72),
                Ingredient("T5_EGG", 36),
                Ingredient("T8_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 90)
            ),
            3 to listOf(
                Ingredient("T7_ALCHEMY_RARE_EAGLE", 1),
                Ingredient("T8_YARROW", 144),
                Ingredient("T7_MULLEIN", 72),
                Ingredient("T7_ALCOHOL", 72),
                Ingredient("T5_EGG", 36),
                Ingredient("T8_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 90)
            )
        )
    ),
    "POTION_MOB_RESET" to mapOf(
        "T3" to mapOf(
            0 to listOf(
                Ingredient("T3_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T3_COMFREY", 16)
            ),
            1 to listOf(
                Ingredient("T3_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T3_COMFREY", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 10)
            ),
            2 to listOf(
                Ingredient("T3_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T3_COMFREY", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 10)
            ),
            3 to listOf(
                Ingredient("T3_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T3_COMFREY", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 10)
            )
        ),
        "T5" to mapOf(
            0 to listOf(
                Ingredient("T5_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T5_TEASEL", 48),
                Ingredient("T4_BURDOCK", 24),
                Ingredient("T2_AGARIC", 12),
            ),
            1 to listOf(
                Ingredient("T5_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T5_TEASEL", 48),
                Ingredient("T4_BURDOCK", 24),
                Ingredient("T2_AGARIC", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 30)
            ),
            2 to listOf(
                Ingredient("T5_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T5_TEASEL", 48),
                Ingredient("T4_BURDOCK", 24),
                Ingredient("T2_AGARIC", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 30)
            ),
            3 to listOf(
                Ingredient("T5_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T5_TEASEL", 48),
                Ingredient("T4_BURDOCK", 24),
                Ingredient("T2_AGARIC", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 30)
            )
        ),
        "T7" to mapOf(
            0 to listOf(
                Ingredient("T7_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T7_MULLEIN", 144),
                Ingredient("T6_FOXGLOVE", 72),
                Ingredient("T3_COMFREY", 72),
                Ingredient("T2_AGARIC", 36),
                Ingredient("T7_ALCOHOL", 36),
            ),
            1 to listOf(
                Ingredient("T7_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T7_MULLEIN", 144),
                Ingredient("T6_FOXGLOVE", 72),
                Ingredient("T3_COMFREY", 72),
                Ingredient("T2_AGARIC", 36),
                Ingredient("T7_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 90)
            ),
            2 to listOf(
                Ingredient("T7_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T7_MULLEIN", 144),
                Ingredient("T6_FOXGLOVE", 72),
                Ingredient("T3_COMFREY", 72),
                Ingredient("T2_AGARIC", 36),
                Ingredient("T7_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 90)
            ),
            3 to listOf(
                Ingredient("T7_ALCHEMY_RARE_PANTHER", 1),
                Ingredient("T7_MULLEIN", 144),
                Ingredient("T6_FOXGLOVE", 72),
                Ingredient("T3_COMFREY", 72),
                Ingredient("T2_AGARIC", 36),
                Ingredient("T7_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 90)
            )
        )
    ),
    "POTION_CLEANSE2" to mapOf(
        "T3" to mapOf(
            0 to listOf(
                Ingredient("T3_ALCHEMY_RARE_ENT", 1),
                Ingredient("T3_COMFREY", 16)
            ),
            1 to listOf(
                Ingredient("T3_ALCHEMY_RARE_ENT", 1),
                Ingredient("T3_COMFREY", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 10)
            ),
            2 to listOf(
                Ingredient("T3_ALCHEMY_RARE_ENT", 1),
                Ingredient("T3_COMFREY", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 10)
            ),
            3 to listOf(
                Ingredient("T3_ALCHEMY_RARE_ENT", 1),
                Ingredient("T3_COMFREY", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 10)
            )
        ),
        "T5" to mapOf(
            0 to listOf(
                Ingredient("T5_ALCHEMY_RARE_ENT", 1),
                Ingredient("T5_TEASEL", 48),
                Ingredient("T3_COMFREY", 24),
                Ingredient("T4_BUTTER", 12),
            ),
            1 to listOf(
                Ingredient("T5_ALCHEMY_RARE_ENT", 1),
                Ingredient("T5_TEASEL", 48),
                Ingredient("T3_COMFREY", 24),
                Ingredient("T4_BUTTER", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 30)
            ),
            2 to listOf(
                Ingredient("T5_ALCHEMY_RARE_ENT", 1),
                Ingredient("T5_TEASEL", 48),
                Ingredient("T3_COMFREY", 24),
                Ingredient("T4_BUTTER", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 30)
            ),
            3 to listOf(
                Ingredient("T5_ALCHEMY_RARE_ENT", 1),
                Ingredient("T5_TEASEL", 48),
                Ingredient("T3_COMFREY", 24),
                Ingredient("T4_BUTTER", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 30)
            )
        ),

        "T7" to mapOf(
            0 to listOf(
                Ingredient("T7_ALCHEMY_RARE_ENT", 1),
                Ingredient("T7_MULLEIN", 144),
                Ingredient("T4_BURDOCK", 72),
                Ingredient("T3_COMFREY", 72),
                Ingredient("T6_BUTTER", 36),
                Ingredient("T7_ALCOHOL", 36),
            ),
            1 to listOf(
                Ingredient("T7_ALCHEMY_RARE_ENT", 1),
                Ingredient("T7_MULLEIN", 144),
                Ingredient("T4_BURDOCK", 72),
                Ingredient("T3_COMFREY", 72),
                Ingredient("T6_BUTTER", 36),
                Ingredient("T7_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 90)
            ),
            2 to listOf(
                Ingredient("T7_ALCHEMY_RARE_ENT", 1),
                Ingredient("T7_MULLEIN", 144),
                Ingredient("T4_BURDOCK", 72),
                Ingredient("T3_COMFREY", 72),
                Ingredient("T6_BUTTER", 36),
                Ingredient("T7_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 90)
            ),
            3 to listOf(
                Ingredient("T7_ALCHEMY_RARE_ENT", 1),
                Ingredient("T7_MULLEIN", 144),
                Ingredient("T4_BURDOCK", 72),
                Ingredient("T3_COMFREY", 72),
                Ingredient("T6_BUTTER", 36),
                Ingredient("T7_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 90)
            )
        )
    ),
    "POTION_ACID" to mapOf(
        "T3" to mapOf(
            0 to listOf(
                Ingredient("T3_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T3_COMFREY", 16)
            ),
            1 to listOf(
                Ingredient("T3_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T3_COMFREY", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 10)
            ),
            2 to listOf(
                Ingredient("T3_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T3_COMFREY", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 10)
            ),
            3 to listOf(
                Ingredient("T3_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T3_COMFREY", 16),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 10)
            )
        ),
        "T5" to mapOf(
            0 to listOf(
                Ingredient("T5_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T5_TEASEL", 48),
                Ingredient("T4_BURDOCK", 24),
                Ingredient("T4_MILK", 12),
            ),
            1 to listOf(
                Ingredient("T5_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T5_TEASEL", 48),
                Ingredient("T4_BURDOCK", 24),
                Ingredient("T4_MILK", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 30)
            ),
            2 to listOf(
                Ingredient("T5_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T5_TEASEL", 48),
                Ingredient("T4_BURDOCK", 24),
                Ingredient("T4_MILK", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 30)
            ),
            3 to listOf(
                Ingredient("T5_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T5_TEASEL", 48),
                Ingredient("T4_BURDOCK", 24),
                Ingredient("T4_MILK", 12),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 30)
            )
        ),
        "T7" to mapOf(
            0 to listOf(
                Ingredient("T7_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T7_MULLEIN", 144),
                Ingredient("T6_FOXGLOVE", 72),
                Ingredient("T6_ALCOHOL", 72),
                Ingredient("T6_MILK", 36),
                Ingredient("T7_ALCOHOL", 36),
            ),
            1 to listOf(
                Ingredient("T7_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T7_MULLEIN", 144),
                Ingredient("T6_FOXGLOVE", 72),
                Ingredient("T6_ALCOHOL", 72),
                Ingredient("T6_MILK", 36),
                Ingredient("T7_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL1", 90)
            ),
            2 to listOf(
                Ingredient("T7_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T7_MULLEIN", 144),
                Ingredient("T6_FOXGLOVE", 72),
                Ingredient("T6_ALCOHOL", 72),
                Ingredient("T6_MILK", 36),
                Ingredient("T7_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL2", 90)
            ),
            3 to listOf(
                Ingredient("T7_ALCHEMY_RARE_DIREBEAR", 1),
                Ingredient("T7_MULLEIN", 144),
                Ingredient("T6_FOXGLOVE", 72),
                Ingredient("T6_ALCOHOL", 72),
                Ingredient("T6_MILK", 36),
                Ingredient("T7_ALCOHOL", 36),
                Ingredient("T1_ALCHEMY_EXTRACT_LEVEL3", 90)
            )
        )
    ),
    "POTION_CLEANSE" to mapOf(
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
    "ALCOHOL" to mapOf(
        "T6" to mapOf(
            0 to listOf(
                Ingredient("T6_POTATO", 1),
            )
        ),
        "T7" to mapOf(
            0 to listOf(
                Ingredient("T7_CORN", 1),
            )
        ),
        "T8" to mapOf(
            0 to listOf(
                Ingredient("T8_PUMPKIN", 1),
            )
        )
    )
)


