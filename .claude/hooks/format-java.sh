#!/usr/bin/env bash
# Stop: if Claude changed Java files this turn, run Spotless once (palantir-java-format) so formatting
# never depends on Claude remembering to do it.
marker="$CLAUDE_PROJECT_DIR/build/.claude-java-changed"
[ -f "$marker" ] || exit 0
rm -f "$marker"
cd "$CLAUDE_PROJECT_DIR" || exit 0
if ! ./gradlew -q spotlessApply >"$CLAUDE_PROJECT_DIR/build/claude-spotless.log" 2>&1; then
  echo "spotlessApply failed (likely a syntax error). See output:" >&2
  tail -20 "$CLAUDE_PROJECT_DIR/build/claude-spotless.log" >&2
  exit 2
fi
exit 0
