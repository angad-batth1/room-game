#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
DIST_DIR="$ROOT_DIR/dist"
PACKAGE_DIR="$ROOT_DIR/release"
JAR_FILE="$DIST_DIR/the-heist.jar"

if ! command -v jpackage >/dev/null 2>&1; then
    echo "jpackage is not installed or not on PATH."
    echo "Install a JDK that includes jpackage, then run this script again."
    exit 1
fi

"$ROOT_DIR/scripts/build-jar.sh"
rm -rf "$PACKAGE_DIR"
mkdir -p "$PACKAGE_DIR"

jpackage \
    --type app-image \
    --name "The Heist" \
    --input "$DIST_DIR" \
    --main-jar "$(basename "$JAR_FILE")" \
    --main-class main.Main \
    --dest "$PACKAGE_DIR"

echo "Packaged app image in $PACKAGE_DIR"
