# AI-Based Offline Signature Verification — Dataset Kit

## Dataset goal
Create a college-student signature dataset for an academic machine-learning project.

### Initial dataset
- 50 participants
- 10 genuine signatures per participant
- 500 genuine signature images total
- Anonymous IDs: STU001 ... STU050

Do not put names, roll numbers, phone numbers, email addresses, or other personal information in image filenames or metadata.

## Folder structure

```text
signature_verification_dataset/
├── metadata.csv
├── collection_tracker.xlsx
├── docs/
│   └── signature_collection_sheet.pdf
├── dataset/
│   └── images/
│       ├── STU001/
│       ├── STU002/
│       └── ...
└── scripts/
    └── create_folders.py
```

## Collection procedure

1. Assign each participant an anonymous ID.
2. Give the participant one collection sheet.
3. Collect 10 naturally written genuine signatures.
4. Scan/photograph the sheet under consistent lighting.
5. Crop each signature into an individual image.
6. Save as PNG where possible.
7. Put images in the participant's folder:
   `dataset/images/STU001/`
8. Name them `genuine_01.png` ... `genuine_10.png`.
9. Update the tracker.

## Image recommendations
- Plain white background.
- Dark ink.
- Avoid shadows and folds.
- Keep the full signature visible.
- Prefer consistent camera/scanner settings.
- Do not alter the signature itself during preprocessing.

## Privacy and consent
Participants should voluntarily agree to their signatures being used for this academic project. Keep the mapping between a real person and their anonymous participant ID separately and securely. Do not publish raw signatures publicly without appropriate permission.

## ML evaluation note
Do not randomly split near-duplicate signatures from the same collection sheet into train/test. We will define a proper subject-aware verification protocol when the dataset is ready.

## Future extension
For the first version, collect genuine signatures only. If the project requires a forgery experiment, we can design a separate, explicitly labeled simulated-forgery protocol later.
