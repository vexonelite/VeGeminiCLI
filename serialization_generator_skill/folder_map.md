
./serialization_generator_skill/
│
├── 📜 core_instruction.md      <-- The "System Prompt" (Role & Sequence)
├── 📜 constraints.md           <-- The "Guardrails" (Anti-efficiency rules)
│
├── 📂 domain_context/          <-- Knowledge Base
│   └── base_delegates.kt       <-- Contains KeywordQueryModelDelegate
│
├── 📂 templates/               
│   ├── interface_tmpl.kt       <-- Pattern for [Feature]QueryModelDelegate
│   └── data_class_tmpl.kt      <-- Pattern for Wtc[Feature]QueryModel
│
└── 📂 code_sample/             <-- Few-shot samples (Input JSON -> Code)
    └── sample_conversion.kt






