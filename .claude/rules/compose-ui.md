---
description: Jetpack Compose UI best practices for composable functions and components
globs:
  - "composeApp/src/**/components/**/*.kt"
  - "composeApp/src/**/*Screen.kt"
  - "composeApp/src/**/*Content.kt"
---

# Compose UI Rules

## State Hoisting
- Composables that display state must NOT own it — receive state as parameters, emit events as lambdas
- Pattern: `fun MyComponent(state: T, onEvent: (Event) -> Unit)`
- Only use `remember { mutableStateOf() }` for truly local UI state (animations, scroll position)

## Recomposition Safety
- All classes passed to Composables must be stable: `data class` with `val` properties, primitives, or `@Stable`/`@Immutable` annotated
- Provide stable `key` in `LazyColumn`/`LazyRow`: `items(list, key = { it.id })` — never use index
- Use `derivedStateOf {}` for state derived from rapidly-changing sources
- Collect ViewModel state with `collectAsStateWithLifecycle()` — not `collectAsState()`

## Side Effects
- `LaunchedEffect(key)` — one-shot coroutine work on key change
- `DisposableEffect(key)` — setup/teardown (listeners, callbacks)
- NEVER call suspend functions, network calls, or DB operations in composable body
- NEVER update MutableState during composition — only in event handlers or LaunchedEffect

## Component Structure
- Every screen composable should have a `@Preview` function
- Lambda parameters go last: `fun MyFun(x: Int, onClick: () -> Unit)`
- Use `Modifier` as first optional parameter with default `Modifier`
- Keep composables small and focused — one responsibility per function

## This Project's Theme
- Colors defined in `components/Colors.kt` (AppColors object) — always use these, never hardcode colors
- Primary font: EBGaramond (serif) for headers/buttons
- Dark theme: BackgroundDark (#1F160E), PrimaryGold (#C47A30), LightBeige (#F2E9DC)
- Standard corner radius: 8.dp (components), 12.dp (dialogs)
