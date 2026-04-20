#!/usr/bin/env python3
"""
generate_game_names.py
Developer tool — NOT part of the app runtime.

Downloads the Albion Online item dump from ao-bin-dumps and extracts
localized names for all ingredients and potions used by the Potion Calculator.

Outputs one JSON file per language to:
  composeApp/src/commonMain/composeResources/files/game_names/{lang}.json

Usage:
  python scripts/generate_game_names.py

Requirements: Python 3.8+  |  No third-party packages needed.
Run again whenever the game receives a major item update.
"""

import json
import sys
import pathlib
import urllib.request

# ---------------------------------------------------------------------------
# Configuration
# ---------------------------------------------------------------------------

DUMP_URL = (
    "https://raw.githubusercontent.com/ao-data/ao-bin-dumps/master"
    "/formatted/items.json"
)

OUTPUT_DIR = (
    pathlib.Path(__file__).parent.parent
    / "composeApp/src/commonMain/composeResources/files/game_names"
)

# ao-bin-dumps locale key → our JSON filename
LANG_MAP = {
    "EN-US": "en",
    "RU-RU": "ru",
    "DE-DE": "de",
    "FR-FR": "fr",
    "ES-ES": "es",
    "PT-BR": "pt",
    "PL-PL": "pl",
    "ZH-CN": "zh",
    "KO-KR": "ko",
    "JA-JP": "ja",
}

# ---------------------------------------------------------------------------
# Ingredient IDs — all 49 entries from en.json
# Two IDs (T6_EGG, T5_MILK) have no dump entry; see INGREDIENT_FALLBACKS below.
# ---------------------------------------------------------------------------
INGREDIENT_IDS = [
    # Herbs
    "T2_AGARIC",
    "T3_COMFREY",
    "T4_BURDOCK",
    "T5_TEASEL",
    "T6_FOXGLOVE",
    "T6_POTATO",
    "T7_CORN",
    "T7_MULLEIN",
    "T8_PUMPKIN",
    "T8_YARROW",
    # Alchemy extracts
    "T1_ALCHEMY_EXTRACT_LEVEL1",
    "T1_ALCHEMY_EXTRACT_LEVEL2",
    "T1_ALCHEMY_EXTRACT_LEVEL3",
    # Rare mob drops (T3)
    "T3_ALCHEMY_RARE_DIREBEAR",
    "T3_ALCHEMY_RARE_EAGLE",
    "T3_ALCHEMY_RARE_ELEMENTAL",
    "T3_ALCHEMY_RARE_ENT",
    "T3_ALCHEMY_RARE_IMP",
    "T3_ALCHEMY_RARE_PANTHER",
    "T3_ALCHEMY_RARE_WEREWOLF",
    # Rare mob drops (T5)
    "T5_ALCHEMY_RARE_DIREBEAR",
    "T5_ALCHEMY_RARE_EAGLE",
    "T5_ALCHEMY_RARE_ELEMENTAL",
    "T5_ALCHEMY_RARE_ENT",
    "T5_ALCHEMY_RARE_IMP",
    "T5_ALCHEMY_RARE_PANTHER",
    "T5_ALCHEMY_RARE_WEREWOLF",
    # Rare mob drops (T7)
    "T7_ALCHEMY_RARE_DIREBEAR",
    "T7_ALCHEMY_RARE_EAGLE",
    "T7_ALCHEMY_RARE_ELEMENTAL",
    "T7_ALCHEMY_RARE_ENT",
    "T7_ALCHEMY_RARE_IMP",
    "T7_ALCHEMY_RARE_PANTHER",
    "T7_ALCHEMY_RARE_WEREWOLF",
    # Eggs
    "T3_EGG",
    "T5_EGG",
    "T6_EGG",       # not in dump → falls back via INGREDIENT_FALLBACKS
    # Dairy
    "T4_BUTTER",
    "T6_BUTTER",
    "T8_BUTTER",
    "T4_MILK",
    "T5_MILK",      # not in dump → falls back via INGREDIENT_FALLBACKS
    "T6_MILK",
    "T8_MILK",
    # Alcohol
    "T6_ALCOHOL",
    "T7_ALCOHOL",
    "T8_ALCOHOL",
]

# For IDs missing from the dump, borrow translations from a sibling item
# that has the same in-game name (e.g. T6_EGG == "Goose Eggs" == T5_EGG).
INGREDIENT_FALLBACKS = {
    "T6_EGG":  "T5_EGG",   # Goose Eggs — T6 variant shares the same name
    "T5_MILK": "T4_MILK",  # Goat's Milk — T5 variant shares the same name
}

# ---------------------------------------------------------------------------
# Potion / food mapping
# Key   = English display name used as key in en.json (what getPotionDisplayName receives)
# Value = representative game item ID whose LocalizedNames we borrow
#
# Notes on non-obvious mappings (confirmed against dump):
#   T5_POTION_REVIVE    → "Gigantify Potion"  (internal ID says REVIVE, but EN name is Gigantify)
#   T5_POTION_STONESKIN → "Resistance Potion" (internal ID says STONESKIN)
#   T5_POTION_SLOWFIELD → "Sticky Potion"
#   T6_POTION_COOLDOWN  → "Poison Potion"
#   T8_POTION_CLEANSE   → "Invisibility Potion"
#   T5_POTION_CLEANSE2  → "Cleansing Potion"  (app calls it "Cleanse Potion")
#
# "Revive Potion" and "Gravitas Potion" have no match in the current dump.
# They are omitted here → JsonGameNameProvider falls back to English for them.
# ---------------------------------------------------------------------------
POTION_ID_MAP = {
    "Healing Potion":      "T4_POTION_HEAL",
    "Energy Potion":       "T4_POTION_ENERGY",
    "Gigantify Potion":    "T5_POTION_REVIVE",
    "Resistance Potion":   "T5_POTION_STONESKIN",
    "Sticky Potion":       "T5_POTION_SLOWFIELD",
    "Poison Potion":       "T6_POTION_COOLDOWN",
    "Invisibility Potion": "T8_POTION_CLEANSE",
    "Cleanse Potion":      "T5_POTION_CLEANSE2",
    "Stoneskin Potion":    "T5_POTION_STONESKIN",
    "Berserk Potion":      "T6_POTION_BERSERK",
    "Pork Omelette":       "T7_MEAL_OMELETTE",
    "Beef Stew":           "T8_MEAL_STEW",
    "Goose Pie":           "T5_MEAL_PIE",
    "Roast Pork":          "T7_MEAL_ROAST",
    "Alcohol":             "T6_ALCOHOL",
    "Tornado in a Bottle": "T6_POTION_TORNADO",
    # Intentionally omitted (not found in dump):
    #   "Revive Potion"   — will fall back to English in app
    #   "Gravitas Potion" — will fall back to English in app
}

# Cities are proper nouns in Albion Online — never translated.
# Copy English values verbatim into every language file.
CITIES_EN = {
    "Caerleon":     "Caerleon",
    "Bridgewatch":  "Bridgewatch",
    "Martlock":     "Martlock",
    "Lymhurst":     "Lymhurst",
    "Fort Sterling": "Fort Sterling",
    "Thetford":     "Thetford",
    "Brecilien":    "Brecilien",
}

# ---------------------------------------------------------------------------
# Script logic
# ---------------------------------------------------------------------------

def download_dump() -> list:
    print(f"Downloading {DUMP_URL} (~24 MB, please wait)...")
    with urllib.request.urlopen(DUMP_URL, timeout=120) as resp:
        return json.loads(resp.read().decode("utf-8"))


def build_index(dump: list) -> dict:
    """Returns {UniqueName: LocalizedNames_dict} for all items with names."""
    idx = {}
    for item in dump:
        if isinstance(item, dict) and "UniqueName" in item and item.get("LocalizedNames"):
            idx[item["UniqueName"]] = item["LocalizedNames"]
    print(f"Indexed {len(idx)} items with localized names.")
    return idx


def get_name(index: dict, item_id: str, ao_lang: str, context: str = "") -> str | None:
    names = index.get(item_id)
    if names is None:
        print(f"  WARNING: item not found in dump: {item_id}  [{context}]",
              file=sys.stderr)
        return None
    value = names.get(ao_lang)
    if value is None:
        print(f"  WARNING: no '{ao_lang}' translation for {item_id}  [{context}]",
              file=sys.stderr)
    return value


def build_language_data(index: dict, ao_lang: str) -> dict:
    ingredients: dict = {}
    for item_id in INGREDIENT_IDS:
        # Use fallback sibling if the exact ID is missing from dump
        lookup_id = INGREDIENT_FALLBACKS.get(item_id, item_id)
        name = get_name(index, lookup_id, ao_lang, f"ingredient {item_id}")
        if name:
            ingredients[item_id] = name

    potions: dict = {}
    for english_name, item_id in POTION_ID_MAP.items():
        name = get_name(index, item_id, ao_lang, f"potion '{english_name}'")
        if name:
            potions[english_name] = name

    return {
        "ingredients": ingredients,
        "potions":     potions,
        "cities":      CITIES_EN,
    }


def main() -> None:
    dump = download_dump()
    print(f"Dump contains {len(dump)} total entries.")
    index = build_index(dump)

    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

    warnings = 0
    for ao_lang, lang_code in LANG_MAP.items():
        print(f"\nGenerating {lang_code}.json  ({ao_lang}) ...")
        # Redirect stderr temporarily to count warnings
        old_stderr = sys.stderr
        import io
        buf = io.StringIO()
        sys.stderr = buf

        data = build_language_data(index, ao_lang)

        sys.stderr = old_stderr
        warn_text = buf.getvalue()
        if warn_text:
            print(warn_text, end="", file=sys.stderr)
            warnings += warn_text.count("WARNING")

        out_path = OUTPUT_DIR / f"{lang_code}.json"
        out_path.write_text(
            json.dumps(data, ensure_ascii=False, indent=2),
            encoding="utf-8",
        )
        print(f"  -> {out_path.name}  "
              f"({len(data['ingredients'])} ingredients, "
              f"{len(data['potions'])} potions)")

    print(f"\n{'='*60}")
    print(f"Done. {len(LANG_MAP)} files written to: {OUTPUT_DIR}")
    if warnings:
        print(f"  {warnings} WARNING(s) — review output above and fix POTION_ID_MAP if needed.")
    else:
        print("  No warnings — all item IDs resolved successfully.")


if __name__ == "__main__":
    main()
