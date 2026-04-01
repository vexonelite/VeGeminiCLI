# Assemble-SerializationTask.ps1 (v2.0)
# Bundles rules, context, data, and work order for the Gemini CLI

$skillDir = Join-Path (Get-Location).Path "serialization_generator_skill"
$outputPath = Join-Path $skillDir "full_bundle_prompt.txt"

Write-Host "--- 🔍 正在組裝 Serialization 生成任務 ---" -ForegroundColor Cyan

# 1. Grab Instructions & Constraints
Write-Host "讀取核心指令與約束條件..."
$corePrompt = Get-Content (Join-Path $skillDir "core_instruction.md") -Raw
$constraints = Get-Content (Join-Path $skillDir "constraints.md") -Raw

# 2. Grab Domain Context (Base Classes/Interfaces)
Write-Host "抓取基礎類別定義 (Domain Context)..."
$domainContext = Get-ChildItem (Join-Path $skillDir "domain_context") -Include *.kt, *.md -Recurse | ForEach-Object {
    $content = Get-Content $_.FullName -Raw
    "檔案: $($_.Name)`n內容:`n$content`n---`n"
} | Out-String

# 3. Grab the 5 JSONs (input_data.md)
Write-Host "讀取待處理的 5 個 JSON 數據..."
$inputData = Get-Content (Join-Path (Get-Location).Path "input_data.md") -Raw

# 4. Grab the Work Order
Write-Host "讀取工單 (task_museum_models.md)..."
$workOrder = Get-Content (Join-Path $skillDir "task_museum_models.md") -Raw

# 5. Final Assembly
$finalPrompt = @"
[SYSTEM_INSTRUCTIONS]
$corePrompt

[CONSTRAINTS]
$constraints

[DOMAIN_CONTEXT]
$domainContext

[INPUT_JSON_DATA]
$inputData

[WORK_ORDER]
$workOrder

請依照 [WORK_ORDER] 執行。輸出必須嚴格遵守 [TARGET_FILE] 協議。
"@

# 6. Output and Copy
$finalPrompt | Out-File -FilePath $outputPath -Encoding utf8
Get-Content $outputPath -Raw | Set-Clipboard

Write-Host "---" -ForegroundColor Gray
Write-Host "✅ 組裝完成！內容已存至剪貼簿。" -ForegroundColor Green
Write-Host "👉 請到 Gemini CLI 貼上並執行。" -ForegroundColor Cyan

