#!/usr/bin/env python3
"""Generate platform icon files for jpackage from the app logo.

Outputs (in this directory):
  ScholarMatch.icns  - macOS (.dmg / .app)
  ScholarMatch.ico   - Windows (.msi / .exe)
  ScholarMatch.png   - Linux (.deb), 512x512

Usage: python3 packaging/make-icons.py
Requires: Pillow  (pip install Pillow)
"""
from __future__ import annotations

import shutil
import struct
import subprocess
import tempfile
from io import BytesIO
from pathlib import Path

from PIL import Image, ImageDraw

ROOT = Path(__file__).resolve().parent.parent
SRC_LOGO = ROOT / "src" / "main" / "resources" / "images" / "logo.png"
OUT_DIR = ROOT / "packaging"

# Icon geometry (fractions of the full canvas)
TILE_MARGIN = 0.02      # tiny transparent gutter so the rounded corners aren't clipped
CORNER_RADIUS = 0.18    # corner radius as a fraction of the tile size
LOGO_PADDING = 0.14     # empty space between the logo and the tile edge
SUPERSAMPLE = 4         # render big, then downscale for smooth corners


def square_canvas(logo: Image.Image, size: int) -> Image.Image:
    """Center the logo on a white rounded-rectangle tile with a transparent margin."""
    s = size * SUPERSAMPLE
    canvas = Image.new("RGBA", (s, s), (0, 0, 0, 0))

    margin = round(s * TILE_MARGIN)
    tile = s - 2 * margin
    radius = round(tile * CORNER_RADIUS)

    mask = Image.new("L", (s, s), 0)
    ImageDraw.Draw(mask).rounded_rectangle(
        (margin, margin, margin + tile, margin + tile), radius=radius, fill=255
    )
    white = Image.new("RGBA", (s, s), (255, 255, 255, 255))
    canvas = Image.composite(white, canvas, mask)

    usable = round(tile * (1 - 2 * LOGO_PADDING))
    scale = min(usable / logo.width, usable / logo.height)
    w, h = max(1, round(logo.width * scale)), max(1, round(logo.height * scale))
    resized = logo.resize((w, h), Image.LANCZOS)
    canvas.paste(resized, ((s - w) // 2, (s - h) // 2), resized)

    return canvas.resize((size, size), Image.LANCZOS)


def write_icns(base: Image.Image, dest: Path) -> None:
    """Write an ICNS file. Prefer macOS `iconutil`; fall back to a manual writer."""
    if shutil.which("iconutil"):
        _write_icns_iconutil(base, dest)
    else:
        _write_icns_manual(base, dest)


def _write_icns_iconutil(base: Image.Image, dest: Path) -> None:
    layout = {
        16: ["icon_16x16.png"],
        32: ["icon_16x16@2x.png", "icon_32x32.png"],
        64: ["icon_32x32@2x.png"],
        128: ["icon_128x128.png"],
        256: ["icon_128x128@2x.png", "icon_256x256.png"],
        512: ["icon_256x256@2x.png", "icon_512x512.png"],
        1024: ["icon_512x512@2x.png"],
    }
    with tempfile.TemporaryDirectory() as tmp:
        iconset = Path(tmp) / "icon.iconset"
        iconset.mkdir()
        for px, names in layout.items():
            img = base.resize((px, px), Image.LANCZOS)
            for name in names:
                img.save(iconset / name)
        subprocess.run(
            ["iconutil", "-c", "icns", str(iconset), "-o", str(dest)], check=True
        )


def _write_icns_manual(base: Image.Image, dest: Path) -> None:
    """Minimal ICNS writer (PNG-backed icon types, macOS 10.7+)."""
    types = {"ic11": 32, "ic12": 64, "ic07": 128, "ic13": 256, "ic08": 256,
             "ic14": 512, "ic09": 512, "ic10": 1024}
    entries = []
    for ostype, px in types.items():
        buf = BytesIO()
        base.resize((px, px), Image.LANCZOS).save(buf, format="PNG")
        data = buf.getvalue()
        entries.append(ostype.encode("ascii") + struct.pack(">I", len(data) + 8) + data)
    body = b"".join(entries)
    dest.write_bytes(b"icns" + struct.pack(">I", len(body) + 8) + body)


def main() -> None:
    if not SRC_LOGO.exists():
        raise SystemExit(f"logo not found: {SRC_LOGO}")
    logo = Image.open(SRC_LOGO).convert("RGBA")

    master = square_canvas(logo, 1024)

    write_icns(master, OUT_DIR / "ScholarMatch.icns")

    ico_sizes = [(16, 16), (24, 24), (32, 32), (48, 48), (64, 64), (128, 128), (256, 256)]
    master.save(OUT_DIR / "ScholarMatch.ico", format="ICO", sizes=ico_sizes)

    master.resize((512, 512), Image.LANCZOS).save(OUT_DIR / "ScholarMatch.png", format="PNG")

    print("wrote:")
    for name in ("ScholarMatch.icns", "ScholarMatch.ico", "ScholarMatch.png"):
        p = OUT_DIR / name
        print(f"  {p.relative_to(ROOT)}  ({p.stat().st_size} bytes)")


if __name__ == "__main__":
    main()
