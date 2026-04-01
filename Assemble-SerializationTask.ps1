# Assemble-SerializationTask.ps1 (v2.2)
# 目的：組裝模型生成任務，並嚴格鎖定輸出路徑與操作規則

# 定義路徑 - 這裡使用您的目錄結構
$skillDir = Join-Path (Get-Location).Path "serialization_generator_skill"
$outputSubDir = "./serialization_generator_skill/output/"
$inputDataPath = Join-Path (Get-Location).Path "input_data.md"
$outputPath = Join-Path $skillDir "full_bundle_prompt.txt"

Write-Host "--- 🔍 正在組裝 Kotlin Serialization 生成任務 ---" -ForegroundColor Cyan

# 1. 抓取核心指令與約束 (Core v1.4 + Constraints)
Write-Host "讀取 v1.4 核心指令與約束條件..."
$corePrompt = Get-Content (Join-Path $skillDir "core_instruction.md") -Raw
$constraints = Get-Content (Join-Path $skillDir "constraints.md") -Raw

# 2. 遞迴抓取 Domain Context (API 基礎類別定義)
Write-Host "抓取知識庫檔案 (Domain Context)..."
$domainContext = Get-ChildItem (Join-Path $skillDir "domain_context") -Include *.kt, *.md -Recurse | ForEach-Object {
    $content = Get-Content $_.FullName -Raw
    "檔案路徑: $($_.FullName)`n內容:`n$content`n---`n"
} | Out-String

# 3. 抓取待處理 JSON 數據 (input_data.md)
Write-Host "讀取 5 個 JSON 數據資料集..."
$inputData = Get-Content $inputDataPath -Raw

# 4. 抓取工單 (task_museum_models.md)
Write-Host "讀取最新工單任務..."
$workOrder = Get-Content (Join-Path $skillDir "task_museum_models.md") -Raw

# 5. 最終組裝 (加入強制路徑聲明)
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

[MANDATORY_SCOPE_LOCK]
1. 所有的 [TARGET_FILE] 必須指向 $outputSubDir
2. 嚴格禁止搜尋或修改 compose_refactor_skill 目錄。
3. 檢查 $outputSubDir 下的檔案：若不存在則 write_file；若已存在則 edit_file (附加)。
"@

# 6. 寫入檔案並自動複製
$finalPrompt | Out-File -FilePath $outputPath -Encoding utf8
Get-Content $outputPath -Raw | Set-Clipboard

Write-Host "---" -ForegroundColor Gray
Write-Host "✅ 組裝完成！內容已複製到剪貼簿。" -ForegroundColor Green
Write-Host "📋 請至 Gemini CLI 貼上。代理人將會寫入至：$outputSubDir" -ForegroundColor Yellow

