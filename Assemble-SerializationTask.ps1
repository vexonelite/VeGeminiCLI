
# Assemble-SerializationTask.ps1 (v2.1)
# Bundles everything and forces the agent to use the local output path

$skillDir = Join-Path (Get-Location).Path "serialization_generator_skill"
$outputSubDir = "./serialization_generator_skill/output"
$outputPath = Join-Path $skillDir "full_bundle_prompt.txt"

Write-Host "--- 🔍 正在組裝 Serialization 生成任務 ---" -ForegroundColor Cyan

# 1. Grab Instructions & Constraints
$corePrompt = Get-Content (Join-Path $skillDir "core_instruction.md") -Raw
$constraints = Get-Content (Join-Path $skillDir "constraints.md") -Raw

# 2. Grab Domain Context
$domainContext = Get-ChildItem (Join-Path $skillDir "domain_context") -Include *.kt, *.md -Recurse | ForEach-Object {
    $content = Get-Content $_.FullName -Raw
    "檔案路徑: $($_.FullName)`n內容:`n$content`n---`n"
} | Out-String

# 3. Grab JSONs and Work Order
$inputData = Get-Content (Join-Path (Get-Location).Path "input_data.md") -Raw
$workOrder = Get-Content (Join-Path $skillDir "task_museum_models.md") -Raw

# 4. Final Assembly
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

[OUTPUT_PATH_REQUIREMENT]
所有的檔案必須儲存在此路徑下：$outputSubDir
如果檔案已存在，請使用 edit_file 進行附加；如果不存在，請使用 write_file 建立新檔。
"@

# 5. Output and Copy
$finalPrompt | Out-File -FilePath $outputPath -Encoding utf8
Get-Content $outputPath -Raw | Set-Clipboard

Write-Host "---" -ForegroundColor Gray
Write-Host "✅ 組裝完成！" -ForegroundColor Green
Write-Host "📋 內容已存至剪貼簿。請到 Gemini CLI 貼上。" -ForegroundColor Yellow
Write-Host "🚀 注意：代理人將會把檔案寫入 $outputSubDir" -ForegroundColor Cyan
