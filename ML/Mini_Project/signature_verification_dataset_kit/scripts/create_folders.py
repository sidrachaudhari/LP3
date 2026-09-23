from pathlib import Path

ROOT = Path("dataset/images")
ROOT.mkdir(parents=True, exist_ok=True)

for i in range(1, 51):
    student = f"STU{i:03d}"
    (ROOT / student).mkdir(parents=True, exist_ok=True)

print("Created folders for STU001 to STU050.")
