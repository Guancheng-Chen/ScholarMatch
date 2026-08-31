# Packaging

Native installers for ScholarMatch are produced with [`jpackage`](https://docs.oracle.com/en/java/javase/21/jpackage/)
(bundled with JDK 14+). Each installer embeds a trimmed Java 21 runtime, so end
users do **not** need a JDK/JRE installed.

| Platform | Artifact | Built on |
| --- | --- | --- |
| macOS (Apple Silicon) | `ScholarMatch-<version>-macOS-arm64.dmg` | `macos-latest` |
| macOS (Intel) | `ScholarMatch-<version>-macOS-x64.dmg` | `macos-13` |
| Windows | `ScholarMatch-<version>-Windows-x64.msi` / `.exe` | `windows-latest` |
| Linux (Debian/Ubuntu) | `ScholarMatch-<version>-Linux-x64.deb` | `ubuntu-latest` |
| Any | `scholarmatch-prev-<version>.jar` (needs JRE 21) | `ubuntu-latest` |

`jpackage` can only build for the OS it runs on, so the full set is produced by
the `.github/workflows/release.yml` matrix.

## Release flow

**New version:** `git tag v1.2.3 && git push origin v1.2.3` — the workflow builds
from the tag and attaches every artifact to its release (created if needed).
The tag must contain `packaging/` and this workflow, so only cut tags from a
branch that has them.

**Back-fill an existing release** (e.g. `v1.0.0`, tagged before this workflow
existed): run **Actions -> Release native installers -> Run workflow** from
`main` and set `release_tag` to `v1.0.0`. The build uses `main`'s tree; the
assets are attached to the `v1.0.0` release.

## Build a `.dmg` locally (macOS)

```bash
JAVA_HOME=$(/usr/libexec/java_home -v 21) mvn -B -DskipTests clean package

mkdir -p target/jp-input && cp target/scholarmatch-prev-*.jar target/jp-input/
rm -f target/jp-input/original-*.jar

jpackage \
  --type dmg \
  --name ScholarMatch \
  --app-version 1.0.0 \
  --input target/jp-input \
  --main-jar "$(cd target/jp-input && ls *.jar)" \
  --main-class com.scholarmatch.app.ScholarMatchApp \
  --icon packaging/ScholarMatch.icns \
  --vendor "Guancheng Chen" \
  --copyright "Copyright (c) 2026 Guancheng Chen" \
  --java-options "-Xmx512m" \
  --java-options "-Dapple.awt.application.appearance=system" \
  --mac-package-identifier com.scholarmatch.app \
  --add-modules java.base,java.desktop,java.net.http,java.prefs,java.sql,jdk.crypto.ec,jdk.unsupported \
  --jlink-options "--strip-debug --no-header-files --no-man-pages --strip-native-commands" \
  --dest target/dist
```

Swap `--type` / `--icon` for `msi`/`exe` + `.ico` on Windows (needs the
[WiX Toolset 3.x](https://github.com/wixtoolset/wix3/releases)) or `deb` + `.png`
on Linux (needs `fakeroot`).

## Icons

`ScholarMatch.icns` / `.ico` / `.png` are generated from
`src/main/resources/images/logo.png`:

```bash
pip install Pillow
python3 packaging/make-icons.py
```

Tweak `TILE_MARGIN`, `CORNER_RADIUS`, `LOGO_PADDING` at the top of the script to
adjust the white rounded-tile framing.

## Notes

- The installers are **not code-signed / notarized**. On macOS users right-click
  the app and choose *Open* the first time (or `xattr -dr com.apple.quarantine
  /Applications/ScholarMatch.app`). On Windows, SmartScreen -> *More info* ->
  *Run anyway*.
- The bundled runtime modules were derived with
  `jdeps --print-module-deps` plus `java.prefs` (FlatLaf), `jdk.crypto.ec` (TLS),
  and `jdk.unsupported`. If a feature fails at runtime with a
  `NoClassDefFoundError` for a `java.*`/`jdk.*` class, add the owning module to
  `--add-modules` in both this README and the workflow.
