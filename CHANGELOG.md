# Changelog

## Public Beta v_9.0 (versionCode 9)

### New Features

**Focus Crafting Calculator (Craft+ exclusive)**
- Added **Basic Spec Level** and **Mastery Level** inputs to the focus section
- Focus cost per batch is automatically reduced based on specialization:
  - General spec: each level = 30 efficiency points
  - Potion mastery: each level = 280 efficiency points
  - Formula: `reducedCost = baseCost × 0.5^(totalPts / 10000)`
  - Example: 100 mastery reduces focus cost ~7× (e.g., 210 → ~30 pts/batch)
- Result card now shows the **effective focus cost per batch** when mastery lowers it
- **Blended return rate**: when available focus runs out mid-session, profit reflects the realistic weighted average across all planned batches

**Focus cost data embedded for all potions**
- Per-tier and per-enchantment focus costs sourced from `ao-bin-dumps/items.json`
- Covers all 14 potion types across T2–T8 and enchantment levels 0–3

### Fixes

- **Focus state isolation**: enabling focus on Craft+ tab no longer leaks into the Craft tab — switching to Craft tab resets the focus toggle
- **Alcohol localization**: "Alcohol" potion category now correctly shows the localized name in all languages (Russian: "Самогон", German: "Schnaps", French: "Alcool", etc.)

### Math Audit

All profit calculation formulas verified against Albion Online game data:
- Return rates: Brecilien 24.8%, other cities 15.25% (from `craftingmodifiers.xml`)
- Focus return rates: Brecilien 47.4%, other cities 42.9%
- Crafting tax: `itemValue × feePerNutrition × 0.001125`
- Market tax: 6.5% premium / 10.5% standard

### Technical

- ProGuard: added explicit keep rule for `logic` package
- 121 unit tests passing

---

## Public Beta v_8.0 (versionCode 8)

- Localization polishing and game item name fixes

## Public Beta v_7.0 and earlier

- Initial public beta releases with core potion profit calculator
