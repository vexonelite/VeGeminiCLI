
# Work Order: Museum API Model Generation (v1.3)
**Project:** Museum Management System
**Output Path:** ./serialization_generator_skill/output/

## STEP 1: Discovery & Model Mapping
Analyze the 5 JSON objects.

## STEP 2: Generate Interfaces
[TARGET_FILE: ./serialization_generator_skill/output/museum_models.kt]
Generate all Delegate Interfaces (prefixed with "the", numeric as `Float?`).
[END_FILE]

## STEP 3: Generate Implementations
[TARGET_FILE: ./serialization_generator_skill/output/wtc_museum_models.kt]
Generate all Wtc-prefixed data classes (use `@Serializable`, override all fields).
[END_FILE]

## STEP 4: Generate Response Envelopes
[TARGET_FILE: ./serialization_generator_skill/output/wtc_museum_models.kt]
Append `WtcMuseumListResponse` and `WtcMuseumDetailResponse` to the existing implementations.
[END_FILE]


