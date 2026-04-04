Generate a new Compose screen for the PotionCalculator app following the project's architecture patterns.

The argument `$ARGUMENTS` is the screen name (e.g., "PotionDetail", "Statistics").

## What to Create

### 1. Screen Composable
**File**: `composeApp/src/androidMain/kotlin/com/greedmitya/albcalculator/${ARGUMENTS}Screen.kt`

Follow existing patterns from CraftScreen.kt and FavoritesScreen.kt:
- Accept `viewModel: CraftViewModel` parameter (or new ViewModel if needed)
- Accept `scrollState: ScrollState` parameter
- Use AppColors for all colors
- Use EBGaramond font for headers
- Include `@Preview` function
- Use Column with verticalScroll for content

### 2. Navigation Integration
Update `CraftScreen.kt` to add the new tab:
- Add new tab index in the `when(selectedTab)` block
- Add navigation item in BottomNavigationBar

### 3. If the screen needs its own ViewModel
Create in `composeApp/src/commonMain/kotlin/com/greedmitya/albcalculator/`:
- Use sealed interface for UI state
- Expose StateFlow (not mutableStateOf for new code)
- Keep it in commonMain (no Android imports)

## Rules
- Match the existing dark theme (BackgroundDark, PrimaryGold, LightBeige)
- Use existing components from `components/` package where possible
- State hoisting: composables receive state, emit events
- Ask about the screen's purpose and features before generating code
