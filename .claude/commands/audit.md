Perform a code quality audit on the PotionCalculator project. Check the codebase against best practices and report findings.

## Audit Checklist

### 1. SOLID Violations
- Check CraftViewModel for new responsibilities added since last audit
- Identify classes with more than one reason to change
- Look for missing interfaces/abstractions

### 2. DRY Violations
- Search for duplicated code patterns (especially in components/)
- Identify repeated validation logic
- Check for duplicate string/constant definitions

### 3. Code Smells
- Search for `!!` (non-null assertions) — report each occurrence with file:line
- Search for `println(` — debug logging in production code
- Search for `printStackTrace` — improper error handling
- Search for hardcoded magic numbers in logic/calculation code
- Search for `TODO` and `FIXME` comments

### 4. Architecture Check
- Verify no Android imports in commonMain
- Check that PotionCraftCalculator remains pure (no side effects)
- Look for HTTP calls outside of dedicated service classes
- Check for business logic in Composable functions

### 5. Dependency Health
- Read libs.versions.toml and check for version conflicts
- Identify dependencies still declared inline in build.gradle.kts
- Flag any deprecated libraries

## Output Format
For each finding, report:
- **Severity**: Critical / Warning / Info
- **Location**: file:line
- **Issue**: What's wrong
- **Fix**: How to fix it

End with an updated overall score (compare to baseline: 4.9/10 from 2026-04-04 audit).
