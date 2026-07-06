#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
BUILD_DIR="$ROOT_DIR/build"
CLASS_DIR="$BUILD_DIR/classes"
JAR_ROOT_DIR="$BUILD_DIR/jar-root"
DIST_DIR="$ROOT_DIR/dist"
MANIFEST_FILE="$BUILD_DIR/MANIFEST.MF"
JAR_FILE="$DIST_DIR/the-heist.jar"

rm -rf "$CLASS_DIR" "$JAR_ROOT_DIR"
mkdir -p "$CLASS_DIR" "$JAR_ROOT_DIR" "$DIST_DIR"

find "$ROOT_DIR/src" -name "*.java" -print0 | xargs -0 javac -d "$CLASS_DIR"
cp -R "$ROOT_DIR/assets" "$JAR_ROOT_DIR/"
cp -R "$CLASS_DIR"/. "$JAR_ROOT_DIR"/

cat > "$MANIFEST_FILE" <<EOF
Main-Class: main.Main

EOF

jar cfm "$JAR_FILE" "$MANIFEST_FILE" -C "$JAR_ROOT_DIR" .
echo "Built $JAR_FILE"
