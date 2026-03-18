# Assemble-GeminiTask.ps1 (v2.6 - 生成實體檔案並自動複製版本)

$skillDir = Join-Path (Get-Location).Path "compose_refactor_skill"
$srcDir = Join-Path (Get-Location).Path "srcProj"
$outputPath = Join-Path $skillDir "full_bundle_prompt.txt"

Write-Host "--- 🔍 正在組裝 Compose UI 重構任務 ---" -ForegroundColor Cyan

# 1. 抓取指令 (System Instructions)
Write-Host "讀取核心指令..."
$corePrompt = Get-Content (Join-Path $skillDir "core_instruction_v2.4.md"), (Join-Path $skillDir "constraints.md") -Raw -ErrorAction SilentlyContinue | Out-String

# 2. 遞迴抓取 Domain Context (包含 compose_core)
Write-Host "抓取知識庫檔案 (含子目錄)..."
$domainContext = Get-ChildItem (Join-Path $skillDir "domain_context") -Include *.kt, *.md -Recurse | ForEach-Object {
    $content = Get-Content $_.FullName -Raw
    "檔案路徑: $($_.FullName)`n內容:`n$content`n---`n"
} | Out-String

# 3. 抓取 Code Samples
Write-Host "抓取重構範例 (Few-shot samples)..."
$samples = Get-ChildItem (Join-Path $skillDir "code_sample") -Include *.kt -Recurse | ForEach-Object {
    $content = Get-Content $_.FullName -Raw
    "範例: $($_.Name)`n內容:`n$content`n---`n"
} | Out-String

# 4. 抓取待重構源碼 (srcProj 下的 RackScreen 等)
Write-Host "抓取待重構原始碼..."
$sources = Get-ChildItem $srcDir -Include *.kt | ForEach-Object {
    $content = Get-Content $_.FullName -Raw
    "待重構源文件: $($_.Name)`n內容:`n$content`n---`n"
} | Out-String

# 5. 抓取工單
Write-Host "讀取當前工單..."
$workOrder = Get-Content (Join-Path $skillDir "current_task_compose_refactor_2.md") -Raw | Out-String

# 6. 最終組裝
$finalPrompt = @"
[SYSTEM_CONTEXT]
$corePrompt

[KNOWLEDGE_BASE]
$domainContext
$samples

[SOURCE_CODE_TO_REFAC]
$sources

[WORK_ORDER]
$workOrder

請根據以上背景知識、範例與工單要求，執行批次重構任務。
"@

# 7. 寫入檔案並存入剪貼簿
$finalPrompt | Out-File -FilePath $outputPath -Encoding utf8
# 這裡將檔案內容讀出並放入剪貼簿，解決字串在變數中可能被截斷的問題
Get-Content $outputPath -Raw | Set-Clipboard

Write-Host "---" -ForegroundColor Gray
Write-Host "✅ 組裝完成！檔案已存至：$outputPath" -ForegroundColor Green
Write-Host "📋 已自動將完整內容存入【剪貼簿】。" -ForegroundColor Yellow
Write-Host "👉 請現在到 Gemini CLI 視窗按 Ctrl+V 貼上。" -ForegroundColor Cyan

