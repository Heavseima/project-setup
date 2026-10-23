# Claude Code — team setup

This repo includes a shared Claude Code setup so that everyone's Claude follows the same architecture and rules.
Everything in `.claude/` and every `CLAUDE.md` is committed. Personal overrides go in `.claude/settings.local.json`
or `CLAUDE.local.md` (both gitignored).

## Where instructions live

| Need | Put it in | In this repo |
|---|---|---|
| Something Claude should always know | `CLAUDE.md` | root `CLAUDE.md` + one per module |
| A repeatable way of doing work | Skill | `.claude/skills/*/SKILL.md` |
| Something that must always happen | Hook | `.claude/hooks/*.sh` wired in `.claude/settings.json` |
| Something Claude must never do | Permissions `deny` | `.claude/settings.json` |
| A focused, separate investigation or review | Subagent | `.claude/agents/*.md` |
| A one-time request | Your prompt | n/a |

## CLAUDE.md files

- **Root `CLAUDE.md`**: stack, commands, module ownership, architecture rules, conventions, database and security
  rules, definition of done, workflow. Loaded in every session.
- **Module `CLAUDE.md`** (e.g. `src/main/java/.../allowance/CLAUDE.md`): that module's business rules from the spec.
  Claude loads it automatically when it works on files in that folder.

When a permanent rule changes, update the relevant `CLAUDE.md` in the same PR.

## Skills (type `/` in Claude Code)

| Skill | Use it to |
|---|---|
| `/new-module <name>` | Scaffold a new module with the standard layout and boundary check |
| `/build-feature <what>` | Implement an endpoint or feature through all MVC layers with tests |
| `/db-migration <what>` | Create the next Flyway migration with the right number and rules |
| `/module-event <what>` | Add an event published by one module and handled by another |
| `/review-changes` | Review your current `git diff` against our rules before committing |
| `/verify` | Run `./gradlew check` and report real results |

Example: `/build-feature allowance: export monthly allowance report as Excel for ADMIN`

## Subagents

| Agent | What it does |
|---|---|
| `feature-reviewer` | Read-only reviewer with a fresh context. Returns Critical/Major/Minor/Verification/Recommendation. |
| `test-writer` | Writes missing tests for a module in an **isolated git worktree**; never changes production code. |

Example: *"Use the feature-reviewer agent to review the allowance export feature."*

## Permissions (`.claude/settings.json`)

| | Commands |
|---|---|
| **Allowed** without asking | `./gradlew build/test/check/compileJava/spotlessApply/bootRun`, `docker compose ps/logs/up -d`, read-only git (`status`, `diff`, `log`, `show`), `ls`, `tree`, `curl` to localhost |
| **Asks first** | `git add/commit/push/checkout/switch/merge/rebase/stash`, `docker compose down`, other `docker` commands |
| **Blocked** | Reading or editing `.env*` secrets (except `.env.example`), `*.pem`, `*.key`, Firebase/service-account JSON; `git push --force`, `git reset --hard`, `rm -rf`, `flywayClean`, deleting Docker volumes |

## Hooks (run automatically)

| Hook | When | What |
|---|---|---|
| `block-dangerous.sh` | before any Bash command | Blocks `rm -rf`, force push, `reset --hard`, `git clean -f`, Flyway clean, `DROP`/`TRUNCATE`, deleting Docker volumes, `cat .env` |
| `protect-migrations.sh` | before Edit/Write | Blocks edits to **existing** `db/migration/V*.sql` files (new files are fine) |
| `mark-java-changed.sh` + `format-java.sh` | after edits / end of turn | Runs `./gradlew spotlessApply` once if Java files changed |

Hooks are a deterministic safety net. They work even if Claude forgets an instruction. Inspect them with `/hooks`.

## Everyday workflow

1. `cd` into the repo and start `claude`. Check the permission mode (Shift+Tab cycles modes).
2. One focused feature per session. Mention files directly with `@path`.
3. Use `/plan` first for database, auth, permission or cross-module work. Challenge the plan before approving.
4. Implement (`/build-feature …`).
5. Watch which files and commands Claude uses.
6. `/verify`. Claude must show real `./gradlew check` output.
7. `/diff` to look at the changes yourself.
8. `/review-changes`, or the `feature-reviewer` agent for important features.
9. `/security-review` for auth, permission or data-access changes.
10. Update `CLAUDE.md` if a permanent rule changed.
11. `/compact` to keep going on the same task; `/clear` before unrelated work.

### Working in parallel

Use git worktrees so two Claude sessions never edit the same checkout:

```bash
claude -w feature-allowance-export
```

Worktrees are created under `.claude/worktrees/` (gitignored).

### Quick reference

| Situation | Use |
|---|---|
| Claude doesn't follow a project rule | Add it to the right `CLAUDE.md` |
| Context feels stale or confused | `/context`, then `/compact` or `/clear` |
| Want to try another approach safely | `/branch <name>` |
| Claude went the wrong way | `/rewind` (Esc Esc) |
| Something is broken in Claude Code itself | `/doctor` |
