#!/usr/bin/env bash
# PreToolUse(Bash): blocks destructive commands even if a permission rule would allow them.
# Exit code 2 = block the tool call; stderr is shown to Claude as the reason.
input="$(cat)"

block() {
  echo "Blocked by .claude/hooks/block-dangerous.sh: $1. Ask the user to run it themselves if it is really needed." >&2
  exit 2
}

# Work on the raw JSON (no jq dependency). Lowercase for SQL keywords.
lower="$(printf '%s' "$input" | tr '[:upper:]' '[:lower:]')"

printf '%s' "$input" | grep -Eq 'rm[[:space:]]+(-[a-zA-Z]*r[a-zA-Z]*f|-[a-zA-Z]*f[a-zA-Z]*r|--recursive[[:space:]]+--force|--force[[:space:]]+--recursive)' && block "recursive force delete (rm -rf)"
printf '%s' "$input" | grep -Eq 'git[[:space:]]+push[^"]*(--force|-f([[:space:]]|"|$)|--force-with-lease)' && block "force push"
printf '%s' "$input" | grep -Eq 'git[[:space:]]+reset[[:space:]]+--hard' && block "git reset --hard"
printf '%s' "$input" | grep -Eq 'git[[:space:]]+clean[[:space:]]+-[a-zA-Z]*f' && block "git clean -f"
printf '%s' "$input" | grep -Eq 'flywayClean|flyway[[:space:]]+clean' && block "Flyway clean (drops the whole schema)"
printf '%s' "$input" | grep -Eq 'docker[[:space:]]+(compose[[:space:]]+down[^"]*[[:space:]]-v|volume[[:space:]]+(rm|prune)|system[[:space:]]+prune)' && block "deleting Docker volumes (local database data)"
printf '%s' "$lower" | grep -Eq 'drop[[:space:]]+(database|schema|table)' && block "DROP DATABASE/SCHEMA/TABLE"
printf '%s' "$lower" | grep -Eq 'truncate[[:space:]]+(table[[:space:]]+)?[a-z_]' && block "TRUNCATE"
printf '%s' "$input" | grep -Eq '(cat|less|more|head|tail|source|\.)[[:space:]]+[^"]*\.env([[:space:]]|"|$)' && block "reading .env (secrets)"

exit 0
