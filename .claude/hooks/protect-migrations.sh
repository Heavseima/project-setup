#!/usr/bin/env bash
# PreToolUse(Edit|MultiEdit|Write): Flyway migrations are immutable once created.
# Editing an existing V*.sql changes its checksum and breaks every database that already ran it.
# New migration files are allowed.
input="$(cat)"
file="$(printf '%s' "$input" | grep -o '"file_path"[[:space:]]*:[[:space:]]*"[^"]*"' | head -1 | sed 's/.*:[[:space:]]*"\(.*\)"/\1/')"

case "$file" in
  */db/migration/V*.sql)
    if [ -e "$file" ]; then
      echo "Blocked by .claude/hooks/protect-migrations.sh: $file already exists. Never edit an existing Flyway migration; create a new V<next>__<module>_<desc>.sql instead (see /db-migration)." >&2
      exit 2
    fi
    ;;
esac
exit 0
