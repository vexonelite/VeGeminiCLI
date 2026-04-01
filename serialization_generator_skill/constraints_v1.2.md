
# Constraints & Guardrails (1.2)

## Anti-Optimization Rules
- **No Consolidation:** You must output a unique Interface and Data Class for every JSON object. Do not merge similar models.
- **No Summarization:** Output every line of code for every property. Never use "..." or "same as above."
- **Sequential Output:** You are strictly forbidden from jumping to the Implementation until the Interface is fully rendered.

## File Output Standards
- **No Line Numbers:** Do not include line numbers in code blocks.
- **Zero Conversational Filler:** Do not explain what you are doing. Output only the [TARGET_FILE] blocks.
- **Imports:** Every implementation file must include:
  `import kotlinx.serialization.*`
  `import com.google.gson.annotations.SerializedName`

## Code Standards
- **Naming:** - Implementation: `Wtc[FeatureName]Model`
  - Interface: `[FeatureName]ModelDelegate`
  - Properties: Prefix with `the` (e.g., `theKeyword`), even if JSON is `Q_Keyword`.
- **Numeric Types:** Map all numeric fields (0 or 0.0) as `Float? = null` for safe API handling.
- **Annotations:** Every field in `Wtc` classes must have both `@SerializedName` and `@SerialName`.

## Scope & Pathing Constraints
- **Local Folder Only:** You are strictly forbidden from searching for files or writing files outside of the `./serialization_generator_skill/` directory tree.
- **Ignore Other Skills:** Do not access or reference the `compose_refactor_skill` folder. 
- **Tool Usage:** You must use the `write_file` tool to save results. Do not just output text blocks; execute the file creation.
- **Explicit Pathing:** All [TARGET_FILE] paths must be relative to the current skill directory: `./serialization_generator_skill/output/`.

