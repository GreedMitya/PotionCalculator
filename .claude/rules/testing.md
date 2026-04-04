---
description: Testing conventions and strategy for unit, integration, and UI tests
globs:
  - "**/*Test*.kt"
  - "**/test/**/*.kt"
  - "**/commonTest/**/*.kt"
  - "**/androidTest/**/*.kt"
---

# Testing Rules

## Test Pyramid
- 70% unit tests (in `commonTest` and `androidUnitTest`)
- 20% integration tests
- 10% UI tests (Compose test rules)
- Target: 80% coverage on business logic (`PotionCraftCalculator`, ViewModels)

## Naming Convention
```kotlin
fun methodName_condition_expectedResult()
// Examples:
fun calculatePotion_withZeroIngredientPrice_returnsZeroCost()
fun calculatePotion_withBrecilienCity_appliesHigherReturnRate()
fun fetchPrices_networkError_returnsErrorState()
```

## Where to Put Tests
- Shared logic (calculator, models, ViewModel) → `commonTest`
- Platform-specific code (storage, asset loading) → `androidTest` or `iosTest`
- UI tests → `androidTest` with Compose test rules
- ALWAYS test in `commonTest` first — only use platform tests when needed

## Fakes Over Mocks
- Prefer hand-written fake implementations over mocking frameworks
- Fakes work across all KMP targets; most mocking libraries are JVM-only
- Example: `FakePotionRepository`, `FakeFavoritesStorage`, `FakeApiService`

## Ktor Network Testing
- Use `MockEngine` for ALL HTTP tests — never hit real servers
- Test: success responses, 4xx/5xx errors, timeouts, malformed JSON
- MockEngine is multiplatform — tests go in `commonTest`

## What to Test
- PotionCraftCalculator: all potion types, all tier/enchantment combos, edge cases (zero prices, max values)
- ViewModel: state transitions (loading → success → error), event handling
- Repository: data mapping, cache behavior
- DO NOT test: trivial getters, data class equals(), simple UI layout

## What NOT to Do
- No `Thread.sleep()` in tests — use `runTest`, `advanceUntilIdle()`, `waitUntil {}`
- No real network calls — always MockEngine
- No flaky tests — deterministic inputs and outputs only
