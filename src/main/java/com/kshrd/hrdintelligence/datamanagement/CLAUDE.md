# Data Management module (Owner 3)

From the spec (ADMIN and INSTRUCTOR):
- File types: TXT, MD, XLSX, CSV, PDF, DOCX, PPTX, PNG, JPEG.
- Folders and files: create, rename, move, delete, duplicate; export a folder as ZIP or download a file.
- The creator or uploader is the **owner**. Owners can share, manage access, change permissions and permanently delete.
- Permissions: Viewer (read-only); Editor (view, download, rename, move, duplicate, share, delete); remove access.
- Users can only access items they own or that are shared with them. This applies to every query, including search.
- Search by name. Filter by extension and ownership (own, shared, all). Sort by name or upload date.
- File bytes are stored in MinIO; the DB holds metadata only.

## Module boundary rules
- Other modules may only use `datamanagement.api` and `datamanagement.api.event`. Everything else in this folder is internal.
- Never import another module's `model`, `repository`, `service` or `dto`. Use its `api/` or listen to its events.
- Reference other modules' records by id (e.g. `studentId`), never as a JPA relation.
- Schema changes go in a new Flyway file `V<NNN>__datamanagement_<desc>.sql` (use `/db-migration`).

## Not decided yet
Entities, columns, enums and formulas are **pending the team ERD**. Don't invent them: ask, or read the ERD once it's added to `docs/`. Update this file when they're decided.
