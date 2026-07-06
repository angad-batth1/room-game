#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
BUILD_DIR="$ROOT_DIR/build"
CLASS_DIR="$BUILD_DIR/classes"

rm -rf "$CLASS_DIR"
mkdir -p "$CLASS_DIR"

find "$ROOT_DIR/src" -name "*.java" -print0 | xargs -0 javac -d "$CLASS_DIR"
java -cp "$CLASS_DIR" main.Main
