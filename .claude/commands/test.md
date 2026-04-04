Run all tests for the PotionCalculator project and provide a detailed report.

Steps:
1. Run `./gradlew test --console=plain`
2. If tests exist, report:
   - Total tests run
   - Passed / Failed / Skipped counts
   - For any failures: test name, assertion message, and the file location
   - Suggestions for fixing failures
3. If no tests are found or coverage is very low, recommend:
   - Which classes need tests most urgently (PotionCraftCalculator, CraftViewModel)
   - Example test cases that should be written
4. Check test report at `composeApp/build/reports/tests/` if available

IMPORTANT: If tests fail, do NOT just report the failure — analyze the root cause and suggest a fix.
