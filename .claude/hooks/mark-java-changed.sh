#!/usr/bin/env bash
# PostToolUse(Edit|MultiEdit|Write): remember that a .java file changed, so the Stop hook formats once per turn.
input="$(cat)"
if printf '%s' "$input" | grep -q '"file_path"[[:space:]]*:[[:space:]]*"[^"]*\.java"'; then
  mkdir -p "$CLAUDE_PROJECT_DIR/build"
  touch "$CLAUDE_PROJECT_DIR/build/.claude-java-changed"
fi
exit 0
