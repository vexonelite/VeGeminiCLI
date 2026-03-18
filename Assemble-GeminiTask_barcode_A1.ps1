# Assemble-GeminiTask.ps1 (v2.1 - 修正解析錯誤版本)

# 1. 設定基礎路徑 (請依你的實際路徑修改)
$skillDir = "./compose_refactor_skill"
$currentTaskFile = "$skillDir/current_task_A1.md"
$currentPath = (Get-Location).Path

Write-Host "--- 正在組裝任務 (含 Compose Core 組件) ---" -ForegroundColor Cyan

# 2. 抓取 System 核心檔案 (System Instructions)
$corePrompt = Get-Content "$skillDir/core_instruction_v2.md", "$skillDir/constraints.md" -Raw | Out-String

# 3. 遞迴抓取 Domain Context (包含 ``compose_core``)
$domainContext = Get-ChildItem "$skillDir/domain_context/" -Include *.kt, *.md -Recurse | ForEach-Object {
    # 先計算相對路徑，避免在字串內進行複雜運算
    $relPath = $_.FullName.Replace($currentPath, "")
    $content = Get-Content $_.FullName -Raw
    "檔案: $relPath`n內容:`n$content`n---`n"
} | Out-String

# 4. 抓取 Samples
$samples = Get-ChildItem "$skillDir/code_sample/" -Include *.kt -Recurse | ForEach-Object {
    $content = Get-Content $_.FullName -Raw
    "範例: $($_.Name)`n內容:`n$content`n---`n"
} | Out-String

# 5. 抓取工單
$workOrder = Get-Content $currentTaskFile -Raw

# 6. 最終組裝
$finalPrompt = @"
[SYSTEM_CONTEXT]
$corePrompt

[KNOWLEDGE_BASE]
$domainContext
$samples

[WORK_ORDER]
$workOrder
"@

# 6. 改為輸出至檔案而非剪貼簿
$tempPromptPath = "$skillDir/temp_full_prompt.txt"
$finalPrompt | Out-File -FilePath $tempPromptPath -Encoding utf8

Write-Host "✅ 已組裝完成並存入檔案：$tempPromptPath" -ForegroundColor Green

# 7. 嘗試直接呼叫 (如果你的 CLI 支援)
# 範例：gemini --file $tempPromptPath
Write-Host "提示：請確認你的 CLI 是否可以透過參數讀取此檔案，以避免貼上當機。" -ForegroundColor Yellow


