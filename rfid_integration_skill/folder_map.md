
```
./rfid_integration_skill/
│
├── 📂 domain_context/
│   ├── 📜 core_patterns.kt        <-- Logic for FmApiResult and KcEventBus
│   └── 📜 rfid_interfaces.kt      <-- Definition of RfidHelper and Tag Delegates
│
├── 📂 reference_implementations/  <-- NEW: Contextual source for C72 and R36
│   ├── 📂 bluebird_sdk_samples/   <-- 
│   │   ├── 📜 RFAccessFragment.java
│   │   └── 📜 BarcodeFragment.java
│   ├── 📂 c72/                    <-- All C72 specific files
│   └── 📂 r36/                    <-- All R36 specific files
│
├── 📂 sdk_mapping/
│   ├── 📜 bluebird_mapping.md     <-- Mapping BlueBird Java methods to Kotlin
│   └── 📜 comparison_table.md     <-- Feature parity across all supported hardware
│
├── 📂 templates/
│   ├── 📜 helper_implementation.kt <-- Template for generating new Hardware Helpers
│   └── 📜 view_model_adapter.kt    <-- Template for ViewModel implementation
│
└── 📜 instruction_prompt.md        <-- System instructions for the AI Agent
```

