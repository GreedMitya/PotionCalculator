# PotionCalculator — Remaining Work Breakdown

**Status Date:** 2026-04-05
**Overall Project Rating:** 5.3/10 → 6.5/10 → **7.5/10** (after Phase 1 + Phase 2 + Phase 3 partial)
**Phase 1 Completion:** 100% ✓
**Phase 2 Completion:** 100% ✓
**Phase 3 Completion:** ~70% (Focus formula TBD, Price History & Unlimited Favorites not started)

---

## Summary of Completed Work (Phase 1)

1. ✓ Extracted price-selection logic → `FetchPricesUseCase`
2. ✓ Extracted favorites persistence → `FavoritesRepository` interface + implementations
3. ✓ Migrated recipe data → JSON asset file (`recipes.json` in `composeResources/files/`)
4. ✓ Fixed SSL trust bypass for release builds (removed X509TrustManager from PlatformModule)
5. ✓ Added comprehensive test suite:
   - 24 ViewModel tests (init, selection, validation, calculation, favorites, price fetch)
   - 9 FetchPricesUseCase tests (success, errors, edge cases)
   - All 69 tests pass with 0 failures

---

## Summary of Completed Work (Phase 2 — Premium Infrastructure)

1. ✓ `AppPremiumRepository` interface + `AppPurchaseResult` sealed interface (commonMain)
2. ✓ `InMemoryAppPremiumRepository` stub for iOS/Desktop/tests
3. ✓ `GooglePlayPremiumRepository` with Google Play Billing Library (androidMain)
4. ✓ `isAppPremiumUnlocked` state + `purchasePremium()` / `restorePurchases()` in CraftViewModel
5. ✓ `PremiumUpgradeScreen` composable (dark theme, feature list, Buy/Restore buttons)
6. ✓ `CraftSubTabRow` component (Craft | Craft+ with lock icon)
7. ✓ Premium gate on Markets tab (shows PremiumUpgradeScreen if not premium)
8. ✓ Premium gate on Craft+ sub-tab (shows PremiumUpgradeScreen if not premium)
9. ✓ DI wiring: platformModule bindings on Android/iOS/Desktop + AppModule factories

---

## Summary of Completed Work (Phase 3 — Premium Features)

1. ✓ **Focus Crafting (placeholder):** `resolveReturnRate()` in PotionCraftCalculator, UI fields in Craft+ tab (Basic/Mastery/Total), returns base rate until formula is confirmed
2. ✓ **Batch Quantity Calculator:** `craftQuantity` state in CraftViewModel, `Craft Runs` input in Craft+ tab, scaled ingredient display in IngredientItem (`"80 (8x10)"`), total profit row in ResultItem, `totalProfitSilver`/`totalCostSilver`/`totalProfitFormatted` in PotionCraftResult
3. ✓ **City Price Monitor (Markets tab):** `FetchMarketDataUseCase` + `MarketsViewModel`, sub-tabs for Herbs/Components/Potions, horizontally scrollable price table with gold highlight on cheapest, `INGREDIENT_CATEGORIES`/`INGREDIENT_DISPLAY_NAMES`/`ALL_CITIES` constants
4. ✓ **Potion Profit Advisor:** `PotionAdvisorUseCase` (top 5 profitable + top 3 for leveling), `AdvisorContent` UI with city selector, ranked result cards with profit/loss color coding
5. ✓ **Multi-city API:** `getPricesMultiCity()` on AlbionMarketRepository, comma-separated locations in single request
6. ✓ **5-tab navigation:** Craft / Markets / Favorites / How to use / Settings, with `ic_markets` / `ic_markets_active` drawable icons
7. ✓ **Test suite expanded:**
   - 10 FetchMarketDataUseCase tests
   - 8 PotionAdvisorUseCase tests
   - 5 PotionCraftCalculator batch tests
   - 7 PotionCraftResult batch tests
   - 6 AlbionMarketRepository multi-city tests
   - 3 CalculateProfitUseCase batch tests
   - Existing tests updated for new constructor params

---

## Remaining Work by Phase

### PHASE 2 — Premium Infrastructure ✅ COMPLETED

- [x] **2.1** Google Play Billing integrated via `GooglePlayPremiumRepository` (billing-ktx 7.1.1)
- [x] **2.2** App premium gated via `isAppPremiumUnlocked` in CraftViewModel (note: `isPremium` toggle remains — it's the Albion Online in-game subscription for tax rates, NOT app premium)
- [x] **2.3** Premium persistence via Google Play purchase query on init (`isPremiumUnlocked()` checks `queryPurchasesAsync`)
- [x] **2.4** `PremiumUpgradeScreen` composable with feature list, Buy/Restore buttons, themed in app style

---

### PHASE 3 — Premium Features (Partially completed)

#### Completed

- [x] **3.1 Focus Calculations (Placeholder):** `resolveReturnRate()` added to PotionCraftCalculator with TODO for actual formula. UI fields (Basic/Mastery/Total) wired in Craft+ tab. Currently returns base rate unchanged.
- [x] **3.2 City Price Monitor:** `FetchMarketDataUseCase` + `MarketsViewModel` + `MarketsScreen` with Herbs/Components/Potions sub-tabs. `getPricesMultiCity()` fetches all 7 cities in one API call. Price table highlights cheapest city in gold.
- [x] **3.5 Potion Profit Advisor:** `PotionAdvisorUseCase` analyzes all potion/tier/enchantment combos. Top 5 profitable + top 3 for leveling. AdvisorContent UI with city selector.
- [x] **3.6 Batch Quantity Calculator:** Craft Runs input in Craft+ tab, scaled ingredient display, total profit row.

#### Remaining

**3.1b Implement Focus Formula (research needed)**
- **File:** `PotionCraftCalculator.kt` → `resolveReturnRate()`
- **Task:** Research actual Albion Online focus crafting formula, implement in resolveReturnRate()
- **Est. Effort:** 2-3 hours (depends on game mechanics research)

**3.3 Add Price History Caching for Premium Users**
- **File:** `composeApp/src/commonMain/kotlin/.../data/PriceHistoryRepository.kt` (new)
- **Task:** Store historical prices (last 7 days, sampled hourly)
- **Est. Effort:** 3-4 hours

**3.4 Add Unlimited Favorites for Premium Users**
- **File:** `CraftViewModel.kt` + `FavoritesRepository.kt`
- **Task:** Cap free tier at 3-5 favorites, unlimited for premium
- **Est. Effort:** 1-2 hours

---

### PHASE 4 — Quality & Polish

#### Medium Priority

**4.1 Extract Animation / Blink Logic to Compose-side State**
- **File:** `CraftViewModel.kt` (currently manages shimmer, blink, delays)
- **Task:** Move animation choreography from ViewModel to Composables
  - ViewModel exposes: `shouldShowShimmer: Boolean` (derived from result state)
  - ViewModel exposes: `blinkingFields: Set<String>` (validation errors to blink)
  - Remove: `RESET_CLEAR_DELAY_MS`, `RESET_SHIMMER_SHOW_DELAY_MS`, `RESET_SHIMMER_HIDE_DELAY_MS`, `ERROR_BLINK_DURATION_MS`
  - Move timing logic to Composable using `LaunchedEffect` + `animateColorAsState()`
  - Result: ViewModel becomes simpler, animations become testable as Compose previews
- **Impact:** Better separation of concerns, easier to test and reuse components
- **Est. Effort:** 2-3 hours
- **Test:** Compose preview tests showing animation states

**4.2 Remove Direct ViewModel Coupling in CraftContent**
- **File:** `composeApp/src/androidMain/kotlin/.../screen/CraftContent.kt` (432 lines)
- **Current State:** Directly reads ViewModel state, calls ViewModel methods
- **Target State:** Receive state via parameters, emit events via lambdas
- **Task:** Extract parameters from ViewModel in parent Composable (CraftScreen), pass down
  - Before: `CraftContent(viewModel: CraftViewModel)`
  - After: `CraftContent(state: CraftState, onEvent: (CraftEvent) -> Unit)`
  - Easier to test, reuse, and preview
- **Impact:** Better composable design, enabler for component composition testing
- **Est. Effort:** 2-3 hours
- **Test:** Compose preview tests with fake state

**4.3 Add Compose UI Tests for Critical Screens**
- **File:** `composeApp/src/androidTest/kotlin/.../screen/CraftContentTest.kt` (new)
- **Task:** Test key user flows:
  - Potion selection → tier/enchantment populated
  - Ingredient price input → enables Calculate button
  - Calculate button click → shows result with profit/margin
  - Error validation → blink effect visible on invalid fields
  - Favorites: add → appears in list, remove → disappears
- **Test Framework:** Compose test rules (`createComposeRule()`)
- **Impact:** Catch UI regressions early
- **Est. Effort:** 3-4 hours
- **Test Coverage Target:** 80% of critical user flows

**4.4 Add ProGuard Rules for Release Builds**
- **File:** `composeApp/proguard-rules.pro` (currently minimal)
- **Task:** Add rules to preserve:
  - Koin injection entry points
  - Serializable models (Ingredient, PotionCraftResult, etc.)
  - Ktor HTTP client configuration
  - Napier logging
  - Game-specific constants (potion IDs, city names)
- **Command to test:** `./gradlew assembleRelease` — no warnings/errors
- **Impact:** Reduces APK size, protects IP, speeds up app startup
- **Est. Effort:** 1-2 hours
- **Test:** Decompile release APK, verify no method counts or obfuscation issues

---

### PHASE 5 — Optional Enhancements (Nice-to-have)

**5.1 Add Feature Module Structure**
- **File:** Create `feature:premium` and `feature:calculator` modules
- **Task:** Separate premium code into feature module with its own DI, UI, logic
  - Reduces coupling between free and premium code
  - Easier to turn off premium feature if needed
- **Est. Effort:** 4-5 hours (requires careful Gradle setup)

**5.2 Implement Offline Mode with SQLite**
- **File:** `composeApp/src/commonMain/.../data/LocalRecipeRepository.kt` (new)
- **Task:** Cache recipes and prices locally, allow calculations without network
  - Use SQLite (via SQLDelight KMP library) to store recipes + price snapshots
  - Sync on app launch if network available
- **Est. Effort:** 3-4 hours

**5.3 Add Settings Screen Enhancements**
- **File:** `SettingsScreen.kt`
- **Task:**
  - Clear cached prices button
  - Export/import favorites (JSON/CSV)
  - App version + build number display
  - Logging: enable/disable Napier logging
  - Premium account management (view subscription, manage billing)
- **Est. Effort:** 2-3 hours

**5.4 Implement Price Alerts**
- **File:** `composeApp/src/commonMain/.../domain/PriceAlertRepository.kt` (new)
- **Task:** Notify user when ingredient/potion price crosses threshold
  - User sets alert: "Notify if T4_BURDOCK drops below 100 silver"
  - Background job checks prices hourly (WorkManager on Android)
  - Premium feature only
- **Est. Effort:** 3-4 hours

---

## Priority Ranking for Next Sessions

| Rank | Task | Phase | Effort | Blocking? | Impact |
|------|------|-------|--------|-----------|--------|
| 1 | Implement Focus formula (research Albion wiki) | 3 | 2-3h | NO | MEDIUM |
| 2 | Add Compose UI tests | 4 | 3-4h | NO | MEDIUM |
| 3 | Add ProGuard rules | 4 | 1-2h | NO | LOW |
| 4 | Extract animation logic | 4 | 2-3h | NO | LOW |
| 5 | Remove ViewModel coupling in CraftContent | 4 | 2-3h | NO | LOW |
| 6 | Add price history caching | 3 | 3-4h | NO | LOW |
| 7 | Add unlimited favorites gating | 3 | 1-2h | NO | LOW |

---

## Recommended Next Session Plan

**Session Goal:** Polish & test premium features, research focus formula

**Tasks (in order):**
1. Test purchase flow end-to-end (Google Play sandbox)
2. Research Albion Online focus crafting formula (wiki/community)
3. Implement focus formula in `resolveReturnRate()`
4. Add Compose UI tests for critical screens
5. Add ProGuard rules for release builds

**Time Estimate:** 5-8 hours

---

## Known Risks & Technical Debt

1. **CraftViewModel still complex** (300+ lines) — consider breaking into smaller focused ViewModels in Phase 4
2. **No server-side receipt validation** — all premium logic is client-side (security risk)
3. **Ktor HTTP client has no timeout/retry** — add in Phase 3 when implementing global monitor
4. **No A/B testing framework** — difficult to measure premium conversion
5. **Dark theme only** — iOS/Desktop stubs exist but untested
6. **Recipe data versioning** — no strategy for adding new potions/enchantments post-release

---

## File Checklist

### Created (Phase 2 + 3)
- [x] `domain/AppPremiumRepository.kt` — interface + sealed result
- [x] `domain/InMemoryAppPremiumRepository.kt` — stub for tests/iOS/Desktop
- [x] `storage/GooglePlayPremiumRepository.kt` — Google Play Billing impl
- [x] `PremiumUpgradeScreen.kt` — upgrade paywall screen
- [x] `components/CraftSubTabRow.kt` — Craft | Craft+ tab selector
- [x] `domain/FetchMarketDataUseCase.kt` — multi-city price fetcher
- [x] `domain/IngredientCategory.kt` — categories, city constants, display names
- [x] `domain/AdvisorResult.kt` — PotionAdvisorResult + AdvisorOutput models
- [x] `domain/PotionAdvisorUseCase.kt` — advisor analysis engine
- [x] `MarketsViewModel.kt` — Markets tab state management
- [x] `MarketsScreen.kt` — Markets UI (4 sub-tabs + advisor)
- [x] `res/drawable/ic_markets.xml` + `ic_markets_active.xml` + `ic_lock.xml`

### Modified (Phase 2 + 3)
- [x] `CraftViewModel.kt` — added premium state, batch quantity, purchasePremium()
- [x] `PotionCraftCalculator.kt` — resolveReturnRate(), craftQuantity param
- [x] `PotionCraftResult.kt` — craftQuantity, totalProfitSilver, totalCostSilver
- [x] `CalculateProfitUseCase.kt` — craftQuantity passthrough
- [x] `AlbionMarketRepository.kt` — getPricesMultiCity()
- [x] `CraftScreen.kt` — 5-tab navigation, MarketsViewModel injection
- [x] `CraftContent.kt` — Craft/Craft+ sub-tabs, focus, batch qty
- [x] `IngredientItem.kt` — scaled quantity display
- [x] `ResultItem.kt` — total profit row
- [x] `AppModule.kt` — new use cases + MarketsViewModel
- [x] Platform modules (Android/iOS/Desktop) — AppPremiumRepository bindings

### Remaining
- [ ] `proguard-rules.pro` — update (add rules)
- [ ] `domain/PriceHistoryRepository.kt` — price history caching
- [ ] Compose UI tests (`CraftContentTest.kt`)

---

## Quick Reference: Constants & Configs

**Magic Numbers (extract to const if not already):**
- `SELL_BUY_RATIO_THRESHOLD = 2.0` (FetchPricesUseCase)
- `BUY_ORDER_MARKUP = 1.1` (FetchPricesUseCase)
- `MIN_VALID_PRICE = 1` (FetchPricesUseCase)
- `PREMIUM_TAX_RATE = 0.065` (PotionCraftCalculator)
- `FREE_TAX_RATE = 0.105` (PotionCraftCalculator)

**New Constants to Define:**
- `PREMIUM_FEATURE_FREE_LIMIT = 5` (favorites)
- `PRICE_CACHE_TTL_MINUTES = 60`
- `GLOBAL_MONITOR_REFRESH_INTERVAL = 3600000ms` (1 hour)

---

**Last Updated:** 2026-04-05 (end of Phase 2 + Phase 3 partial)
**Next Session Focus:** Focus formula research + UI testing + ProGuard
**Questions?** See CLAUDE.md, CLAUDE.local.md, or check existing plan at `.claude/plans/velvet-tickling-parnas.md`
