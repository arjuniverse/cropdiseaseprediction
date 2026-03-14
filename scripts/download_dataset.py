"""
Download Crop Production in India dataset from Kaggle using kagglehub.
Run this script once to populate the data/ folder. Requires Python 3.8+ and kagglehub.
"""
try:
    import kagglehub
except ImportError:
    print("ERROR: kagglehub is not installed. Run: pip install kagglehub")
    exit(1)

import os

# Download dataset
path = kagglehub.dataset_download("abhinand05/crop-production-in-india")
print("Downloaded to:", path)

# Copy or symlink to project data folder for Java to read
project_root = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
data_dir = os.path.join(project_root, "data", "crop-production-in-india")
os.makedirs(data_dir, exist_ok=True)

# List files and copy them (kagglehub downloads to cache; we copy to project)
import shutil
for f in os.listdir(path):
    src = os.path.join(path, f)
    if os.path.isfile(src):
        dst = os.path.join(data_dir, f)
        shutil.copy2(src, dst)
        print("Copied:", f, "->", dst)

print("Done. Data is in:", data_dir)
