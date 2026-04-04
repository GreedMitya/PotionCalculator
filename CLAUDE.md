# PotionCalculator

Kotlin Multiplatform potion crafting profit calculator for Albion Online. Built with Jetpack Compose Multiplatform, MVVM architecture. Android is the primary target; iOS/Desktop are stubs.

## Architecture

- **Pattern**: MVVM — ViewModel in `commonMain`, UI in `androidMain`
- **Layers**: UI (screens/components) → ViewModel → Logic/Models
- **Known tech debt**: `CraftViewModel` is a God Object handling state, HTTP, favorites, validation, and animations. All new logic MUST be extracted into separate classes (Repository, UseCase, ApiService) — NEVER add more responsibilities to CraftViewModel.
- **KMP split**: Models, ViewModel, calculator logic in `commonMain`. All UI, storage, asset loading in `androidMain`.

## Build & Test

```bash
./gradlew build                    # Full build
./gradlew test                     # Run all tests
./gradlew :composeApp:test         # Module tests only
./gradlew assembleDebug            # Debug APK
./gradlew assembleRelease          # Release APK (requires keystore)
./gradlew connectedAndroidTest     # Instrumented tests
```

## Key Files

- `composeApp/src/commonMain/kotlin/.../CraftViewModel.kt` — main state manager (God Object, needs refactoring)
- `composeApp/src/commonMain/kotlin/.../logic/PotionCraftCalculator.kt` — pure calculation logic (good design, keep it pure)
- `composeApp/src/commonMain/kotlin/.../model/PotionRecipeRepository.kt` — hardcoded ingredient database (~1600 lines)
- `composeApp/src/androidMain/kotlin/.../CraftScreen.kt` — root composable with tab navigation
- `composeApp/src/androidMain/kotlin/.../CraftContent.kt` — main craft form UI
- `composeApp/src/androidMain/kotlin/.../storage/FavoritesStorage.kt` — DataStore persistence
- `composeApp/src/androidMain/kotlin/.../components/` — 28 reusable UI components
- `gradle/libs.versions.toml` — version catalog (some deps still inline in build.gradle.kts)

## Hard Rules

- **NEVER add HTTP calls, persistence, or business logic directly to CraftViewModel.** Extract to a separate class first.
- **NEVER put Android-specific imports in `commonMain`.** Use expect/actual or interfaces for platform code.
- **NEVER commit keystore files, local.properties, or API keys.**
- **NEVER use `!!` (non-null assertion).** Use `?.`, `?:`, or `requireNotNull()` with a message.
- **ALWAYS write tests in `commonTest` for shared logic.** Platform-specific tests go in `androidTest`.
- **ALWAYS use the version catalog** (`libs.versions.toml`) for dependency versions. No inline version strings in build.gradle.kts.
- **IMPORTANT**: `PotionCraftCalculator` must remain a pure function with no side effects.
- **IMPORTANT**: Magic numbers (tax rates, return rates) must be named constants, not literals.

## Code Style (non-default rules only)

- 4-space indentation (Kotlin standard)
- Trailing commas in multi-line parameter/argument lists
- Named arguments when calling functions with 3+ parameters
- Expression bodies for single-expression functions
- `data class` for all models, `sealed interface` for state/result types

## API

- Albion Online Data Project: `https://{server}.albion-online-data.com/api/v2/stats/prices/{itemIds}.json`
- Servers: `west` (Americas), `europe` (Europe), `east` (Asia)
- IMPORTANT: API responses may have zero prices — always handle null/zero gracefully

## Workflow

- Branch naming: `feature/description`, `fix/description`, `refactor/description`
- Run `./gradlew test` before committing
- PRs required for main branch
- Commit messages: imperative mood, explain "why" not "what"

