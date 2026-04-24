# Changelog

## v1.2.0 (versionCode 14) — 2026-04-24

### UI Improvements

**Adaptive layout for all screen sizes**
- Content now adapts to narrow screens (< 380dp): side padding reduced from 24dp → 16dp
- Maximum content width capped at 600dp and centered for tablets and foldables
- Consistent title position across all 5 screens (60dp top offset everywhere)

**Input field alignment fixes**
- `SelectorBlock` dropdown boxes now have a guaranteed minimum height of 48dp, matching `InputField` exactly — no more size mismatch between City and Fee fields
- City + Fee row uses `verticalAlignment = Alignment.Bottom` so input boxes always sit at the same level regardless of label line count
- Button text now truncates with ellipsis on narrow screens instead of overflowing; `max = 160dp` cap removed so buttons fill their available weight on wide screens

**Craft+ premium screen redesign**
- Features box anchored to top, price + buttons anchored to bottom — adapts to any screen height without scrolling
- Consistent layout across both entry points (Markets tab and Craft+ tab)
- Reduced icon, spacers, and font sizes for a more compact, focused presentation

### Bug Fixes

- **Craft+ tab for non-premium users**: replaced `return` inside composable lambda with proper `if/else` branching — eliminates the "weird UI mix" where craft content and premium screen were shown simultaneously

### What's New (Play Store)
- UI now adapts cleanly to narrow phones, wide phones, and foldables
- Premium upgrade screen redesigned to fit on one screen without scrolling
- Input fields now consistent in size across all selector/input pairs

---

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
