Extract logic from CraftViewModel into a properly separated class following Clean Architecture principles.

The argument `$ARGUMENTS` describes what to extract (e.g., "API calls", "favorites management", "validation logic").

## Process

### 1. Analyze Current State
- Read `composeApp/src/commonMain/kotlin/com/greedmitya/albcalculator/CraftViewModel.kt`
- Identify all code related to the extraction target ($ARGUMENTS)
- Map dependencies: what does this code need? What depends on it?

### 2. Design the Extraction
Based on what's being extracted:

**If HTTP/API calls** → Create `ApiService` interface + implementation:
- File: `commonMain/.../data/AlbionApiService.kt`
- Use sealed `NetworkResult<T>` for return types
- Configure Ktor client with timeouts and error handling
- Keep in commonMain (Ktor is multiplatform)

**If persistence/favorites** → Create `Repository` interface + implementation:
- Interface: `commonMain/.../data/FavoritesRepository.kt`
- Implementation: `androidMain/.../data/FavoritesRepositoryImpl.kt`
- Use coroutines (suspend functions)

**If business logic** → Create `UseCase` class:
- File: `commonMain/.../domain/CalculatePotionUseCase.kt`
- Pure function, no state, injectable dependencies
- Takes explicit inputs, returns explicit outputs

**If validation** → Create `Validator` class:
- File: `commonMain/.../domain/CraftInputValidator.kt`
- Pure functions returning validation results
- Reusable across ViewModels

### 3. Execute
- Create the new class with proper interface
- Update CraftViewModel to use the new class (inject via constructor or parameter)
- Move related tests or create new ones in commonTest
- Verify the app still compiles: `./gradlew build`
- Run tests: `./gradlew test`

### 4. Rules
- NEVER break existing functionality
- NEVER leave CraftViewModel with dangling references
- The new class MUST be in commonMain unless it requires platform APIs
- The new class MUST have at least one unit test
- Use interface + implementation pattern for testability
