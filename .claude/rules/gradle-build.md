---
description: Gradle build configuration and version catalog rules
globs:
  - "**/build.gradle.kts"
  - "**/settings.gradle.kts"
  - "**/libs.versions.toml"
  - "**/gradle.properties"
---

# Gradle & Build Rules

## Version Catalog (`libs.versions.toml`)
- ALL dependency versions MUST be in `[versions]` section — no inline version strings in build.gradle.kts
- Naming: versions in `camelCase`, libraries in `kebab-case`, plugins in `kebab-case`
- Group related dependencies together with comments
- Use `[bundles]` for libraries always used together (e.g., ktor-client, testing)

## Known Issues to Fix
- `kotlin("plugin.serialization")` version 1.9.22 CONFLICTS with Kotlin 2.1.21 — must align to same Kotlin version
- Several dependencies declared inline in `composeApp/build.gradle.kts` should migrate to version catalog:
  - Ktor (2.3.5), Accompanist (0.28.0), DataStore (1.0.0), Splash Screen (1.0.1)
  - kotlinx-serialization-json (1.6.0)

## Build Configuration
- `compileSdk = 35`, `minSdk = 24`, `targetSdk = 35`
- Java compatibility: JVM 11
- ProGuard/R8 enabled for release builds
- Configuration cache and build caching enabled in gradle.properties

## Rules
- NEVER edit `gradle-wrapper.properties` unless upgrading Gradle intentionally
- NEVER add dependencies without checking KMP compatibility first
- When adding a new dependency: add version to `[versions]`, library to `[libraries]`, use in build.gradle.kts via `libs.` accessor
- Keep `gradle.properties` JVM args reasonable (currently 4096M — sufficient)
