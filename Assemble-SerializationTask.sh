#!/bin/zsh
# Assemble-SerializationTask.sh (v1.1 for macOS)
# 修正了 DOMAIN_CONTEXT 在 loop 中遺失內容的問題

SKILL_DIR="./serialization_generator_skill"
OUTPUT_SUB_DIR="./serialization_generator_skill/output/"
INPUT_DATA_PATH="./input_data.md"
OUTPUT_FILE="${SKILL_DIR}/full_bundle_prompt.txt"

echo "--- 🔍 正在組裝 Kotlin Serialization 生成任務 (macOS) ---"

# 1. 抓取指令與約束
CORE_PROMPT=$(cat "${SKILL_DIR}/core_instruction.md")
CONSTRAINTS=$(cat "${SKILL_DIR}/constraints.md")

# 2. 遞迴抓取 Domain Context (修正版)
DOMAIN_CONTEXT=""
while read -r f; do
    content=$(cat "$f")
    DOMAIN_CONTEXT="${DOMAIN_CONTEXT}檔案路徑: $f\n內容:\n$content\n---\n"
done < <(find "${SKILL_DIR}/domain_context" -type f \( -name "*.kt" -o -name "*.md" \))

# 3. 抓取 JSON 與 工單
INPUT_DATA=$(cat "$INPUT_DATA_PATH")
WORK_ORDER=$(cat "${SKILL_DIR}/task_museum_models.md")

# 4. 最終組裝
FINAL_PROMPT="[SYSTEM_INSTRUCTIONS]
$CORE_PROMPT

[CONSTRAINTS]
$CONSTRAINTS

[DOMAIN_CONTEXT]
$DOMAIN_CONTEXT

[INPUT_JSON_DATA]
$INPUT_DATA

[WORK_ORDER]
$WORK_ORDER

[MANDATORY_SCOPE_LOCK]
1. 所有的 [TARGET_FILE] 必須指向 $OUTPUT_SUB_DIR
2. 嚴格禁止搜尋或修改 compose_refactor_skill 目錄。
3. 檢查 $OUTPUT_SUB_DIR 下的檔案：若不存在則 write_file；若已存在則 edit_file (附加)。"

# 5. 輸出並複製
echo "$FINAL_PROMPT" > "$OUTPUT_FILE"
echo "$FINAL_PROMPT" | pbcopy

echo "---"
echo "✅ 組裝完成！內容已成功抓取並存入剪貼簿。"
echo "🚀 預期寫入路徑：$OUTPUT_SUB_DIR"

