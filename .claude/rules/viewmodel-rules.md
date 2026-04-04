---
description: Architecture rules for ViewModels — MVVM, UDF, layer separation
globs:
  - "**/*ViewModel*.kt"
  - "**/logic/**/*.kt"
  - "**/model/**/*.kt"
---

# ViewModel & Architecture Rules

## CRITICAL: CraftViewModel is a God Object
The current `CraftViewModel` handles state, validation, HTTP calls, favorites persistence, calculations, and animations. This is the #1 tech debt item. When modifying it:
- EXTRACT logic into separate classes — do NOT add more responsibilities
- New HTTP calls → create an `ApiService` or `Repository` class
- New persistence → create a `Repository` class
- New calculations → keep in `PotionCraftCalculator` or create a new UseCase

## Unidirectional Data Flow (UDF)
- State flows DOWN: ViewModel → UI (via StateFlow or Compose state)
- Events flow UP: UI → ViewModel (via method calls or sealed Event classes)
- NEVER write state during composition — only in event handlers

## Sealed UI State (target pattern)
```kotlin
sealed interface CraftUiState {
    data object Loading : CraftUiState
    data class Success(val result: PotionCraftResult) : CraftUiState
    data class Error(val message: String) : CraftUiState
}
```
Expose ONE `StateFlow<CraftUiState>`, not dozens of individual state properties.

## Layer Separation
- **ViewModel**: State management + UI event handling ONLY
- **UseCase/Interactor**: Business logic (calculations, transformations)
- **Repository**: Data access (API, local storage, assets)
- **ApiService**: HTTP client wrapper with sealed Result type

## commonMain Rules
- NEVER import `android.*` or `androidx.*` in commonMain
- Use `expect`/`actual` or interface injection for platform-specific APIs
- All shared logic must be testable without Android framework

## Error Handling
- Wrap API calls in `sealed interface NetworkResult<T> { Success(data: T), Error(exception) }`
- Map network errors to user-facing messages in ViewModel
- NEVER use `printStackTrace()` — use proper logging or error state
- NEVER silently swallow exceptions

## PotionCraftCalculator
- MUST remain a pure function: no state, no side effects, no I/O
- Input → Output only. All dependencies passed as parameters.
- This is the best-designed part of the codebase — protect it.
