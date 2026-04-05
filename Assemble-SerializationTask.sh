
#!/bin/zsh
# Assemble-SerializationTask.sh (v1.0 for macOS)
# 目的：組裝模型生成任務，並針對 Mac 環境調整路徑與剪貼簿操作

# 定義路徑
SKILL_DIR="./serialization_generator_skill"
OUTPUT_SUB_DIR="./serialization_generator_skill/output/"
INPUT_DATA_PATH="./input_data.md"
OUTPUT_FILE="${SKILL_DIR}/full_bundle_prompt.txt"

echo "--- 🔍 正在組裝 Kotlin Serialization 生成任務 (macOS) ---"

# 1. 抓取核心指令與約束 (Core v1.4 + Constraints)
CORE_PROMPT=$(cat "${SKILL_DIR}/core_instruction.md")
CONSTRAINTS=$(cat "${SKILL_DIR}/constraints.md")

# 2. 遞迴抓取 Domain Context (API 基礎類別定義)
DOMAIN_CONTEXT=""
# 使用 find 尋找 .kt 與 .md 檔案
find "${SKILL_DIR}/domain_context" -type f \( -name "*.kt" -o -name "*.md" \) | while read -r f; do
    content=$(cat "$f")
    DOMAIN_CONTEXT="${DOMAIN_CONTEXT}檔案路徑: $f\n內容:\n$content\n---\n"
done

# 3. 抓取待處理 JSON 數據 (input_data.md)
INPUT_DATA=$(cat "$INPUT_DATA_PATH")

# 4. 抓取工單 (task_museum_models.md)
WORK_ORDER=$(cat "${SKILL_DIR}/task_museum_models.md")

# 5. 最終組裝
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

# 6. 寫入檔案並使用 pbcopy 複製到剪貼簿
echo "$FINAL_PROMPT" > "$OUTPUT_FILE"
echo "$FINAL_PROMPT" | pbcopy

echo "---"
echo "✅ 組裝完成！"
echo "📋 內容已使用 pbcopy 存入剪貼簿。請到 Gemini CLI 貼上。"
echo "🚀 預期寫入路徑：$OUTPUT_SUB_DIR"
