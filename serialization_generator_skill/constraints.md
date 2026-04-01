
# Constraints & Guardrails (1.3)

## Scope & Pathing
- **Directory Lock:** Do not access any files or folders starting with `compose_refactor_skill`. 
- **Tool Restriction:** Use `FindFiles` only within the current `./serialization_generator_skill/` path.
- **Absolute Output:** All generated files MUST be saved into `./serialization_generator_skill/output/`. Do not assume any other project root.

## Anti-Optimization Rules
- **No Consolidation:** You must output a unique Interface and Data Class for every JSON object. Do not merge similar models.
- **No Summarization:** Output every line of code for every property. Never use "..." or "same as above."
- **Sequential Output:** You are strictly forbidden from jumping to the Implementation until the Interface is fully rendered.

## File Output Standards
- **No Line Numbers:** Do not include line numbers in code blocks.
- **Zero Conversational Filler:** Do not explain what you are doing. Output only the [TARGET_FILE] blocks.
- **Numeric Types:** Map all numeric fields (0, 0.0, 1) as `Float? = null` for safe API handling.
- **Annotations:** Every field in `Wtc` classes must have both `@SerializedName` and `@SerialName`.

