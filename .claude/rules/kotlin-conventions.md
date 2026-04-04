---
description: Kotlin coding conventions for all Kotlin source files
globs:
  - "**/*.kt"
---

# Kotlin Conventions

## Naming
- Classes/interfaces: `PascalCase`
- Functions/properties: `camelCase`
- Constants: `SCREAMING_SNAKE_CASE`
- Packages: all lowercase, no underscores
- Acronyms: 2-letter = all caps (`IO`), longer = capitalize first only (`Http`)

## Data Modeling
- Use `data class` for all models and DTOs
- Use `sealed interface` for type-safe state hierarchies and result types
- Use `enum class` only for fixed, exhaustive sets (cities, tiers)
- Prefer `val` over `var` everywhere — immutable by default

## Null Safety
- NEVER use `!!` — use `?.`, `?:`, `let {}`, or `requireNotNull("reason")`
- Use `?.let { }` for nullable chains, not nested `if (x != null)` blocks
- Default to non-nullable types; make nullable only when truly optional

## Functions
- Single-expression functions use expression body: `fun name() = value`
- Use trailing commas in multi-line parameter lists
- Use named arguments for functions with 3+ parameters
- Extension functions for utility operations on existing types

## Scope Functions
- `apply {}` — configure an object after creation
- `also {}` — perform side effects, return original
- `let {}` — transform nullable or scoped value
- `run {}` — compute a result from an object
- `with(obj) {}` — multiple operations on same object

## Constants
- Extract magic numbers to named constants: `private const val BRECILIEN_RETURN_RATE = 0.248`
- Group related constants in a companion object or top-level `object`
