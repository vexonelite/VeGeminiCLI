
# Work Order: Museum API Model Generation (v1.2)
**Project:** Museum Management System
**Architecture:** Kotlin Serialization + Interface Delegation

## STEP 1: Discovery & Model Mapping
Analyze the 5 JSON objects in [INPUT_JSON_DATA]. 
- **Query 1 & 2:** Map to `MuseumGeneralQueryModel` and `MuseumSpecificQueryModel`.
- **Response 1 & 2:** Map to `MuseumBaseItem`.
- **Response 3:** Map to `MuseumDetailItem` with nested children (Author, Detail1, Detail2, AttachFile).

---

## STEP 2: Generate Interfaces (museum_models.kt)
[TARGET_FILE: museum_models.kt]
Generate all Delegate Interfaces. 
- Must inherit from `KeywordQueryModelDelegate` where applicable.
- All numeric fields must be `Float?`.
- Property names must be prefixed with "the".
[END_FILE]

---

## STEP 3: Generate Implementations (wtc_museum_models.kt)
[TARGET_FILE: wtc_museum_models.kt]
Generate all Wtc-prefixed data classes.
- Use `@Serializable`.
- Use both `@SerializedName` and `@SerialName` for every field.
- Every field must use the `override` keyword.
- Numeric fields must be `Float? = null`.
- String fields must be `String = ""`.
[END_FILE]

---

## STEP 4: Generate Response Envelopes (wtc_museum_models.kt)
[TARGET_FILE: wtc_museum_models.kt]
Append the following to the implementation file:
- `WtcMuseumListResponse`: Wraps `List<WtcMuseumBaseItem>`.
- `WtcMuseumDetailResponse`: Wraps `WtcMuseumDetailItem`.
- Must inherit from `BaseRestfulApiResponse`.
[END_FILE]

## MANDATORY FINAL CHECK
- Ensure NO line numbers.
- Ensure NO conversational filler.
- Ensure every JSON field is accounted for.

