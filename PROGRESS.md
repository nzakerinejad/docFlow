# docFlow progress and handoff

Last updated: 2026-10-17.

## Goal

Build an AI-powered tool for searching company documents, conversations, files,
and assets across Slack, Confluence, Git, Jira, and other platforms. Company
accounts must eventually be connected, with authentication and document access
permissions enforced.

## Collaboration agreement

- Work step by step. Ask the user to confirm before implementing each new step.
- Steps 1 and 2 were explicitly approved and implemented.
- Step 3 is postponed. Do not begin platform integration or AI work yet.
- Current priority: review and verify everything implemented so far with the user.

## Current implementation

Backend: Java 21 / Spring Boot, in `backend/`.

- `GET /api/search?query=onboarding` accepts a required, nonblank query up to
  1,000 characters and optional `sources`.
- Sources: CONFLUENCE, SLACK, GIT, JIRA, FILE_STORAGE.
- Response: `{ "query": "onboarding", "connectedSources": [], "results": [] }`.
- Result fields: id, source, title, snippet, url.
- SearchConnector interface exists; real platform integrations, authentication,
  and AI search are not implemented. Empty results are currently expected.
- Swagger: http://localhost:8080/swagger-ui.html

Frontend: React / TypeScript / Vite / Cloudflare Worker, in
`frontend/enterprise-search/`.

- Step 1: starter page replaced by search box, Search button, Enter submission,
  whitespace validation, responsive styling, and accessible labels/statuses.
- Step 2: requests `/api/search?query=...`; Worker forwards requests to
  `BACKEND_URL`. Includes loading, disabled controls during search, errors,
  no-connected-sources message, no-results message, and result cards with links.
- Result links permit only HTTP/HTTPS. Requests have timeouts.
- `.dev.vars.example` documents `BACKEND_URL=http://127.0.0.1:8080`.
- Local ignored `.dev.vars` was created with that value. Recreate from the
  example if missing. Production requires a reachable backend origin binding.
- No platform credentials have been configured.

## Run locally

Backend, from repository root:

```sh
cd backend
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk env
./mvnw spring-boot:run
```

Frontend, in a separate terminal from repository root (Node.js 24):

```sh
cd frontend/enterprise-search
# npm ci is needed only for initial dependency setup or a clean reinstall.
# Copy only if .dev.vars is absent:
# cp .dev.vars.example .dev.vars
npm run dev
```

Open the URL printed by Vite. Dependencies were present when checked with
`npm ls --depth=0`.

## Verification completed and remaining

- Frontend production build (`npm run build`) passed after Step 2.
- Frontend lint (`npm run lint`) passed.
- `git diff --check` passed.
- Live frontend Worker returned HTTP 502 and JSON error when backend was
  unreachable, as intended.
- Successful frontend-to-backend search was verified after fixing Worker
  redirect handling: `/api/search?query=onboarding` on port 5173 returned
  HTTP 200 with the expected empty results and connectedSources arrays.
- Root cause of the earlier HTTP 502: Cloudflare Worker fetch does not support
  `redirect: 'error'`. With user approval, switched to `redirect: 'manual'`
  and explicitly reject 3xx responses. Diagnostic error logging was also added
  with user approval. Build, lint, and diff checks passed after the fix.
- The redirect fix and diagnostic logging are included in the follow-up Git
  checkpoint requested by the user.
- The user confirmed the search works in the browser after this fix.
- Populated result rendering has NOT been tested; no sources are connected yet.
- The temporary frontend server started for verification was stopped.

## Next review with the user

1. Start backend and frontend; resolve port 8080 if necessary.
2. Confirm empty and whitespace-only queries show validation.
3. Search for onboarding by button and by Enter; inspect browser Network for
   `/api/search?query=onboarding`, HTTP 200, and the expected empty response.
4. Confirm the UI explains that no company sources are connected.
5. Stop the backend, retry, and confirm a useful error appears and retry works
   after restarting the backend.
6. Review changes together. Ask before implementing fixes or the next step.

## Persistence

This is a progress summary, not a verbatim conversation export.
Code and this document are included in the search interface Git checkpoint.
An unrelated `.idea/` directory is untracked; leave it alone.
Use `git log -1` and `git status` to check the latest checkpoint and any later
changes. The user requested committing and pushing this checkpoint to origin.
Do not commit secrets or `.dev.vars`.

To reopen a saved Codex conversation from this project directory:

```sh
codex resume
# Or reopen the most recent session:
codex resume --last
```

If starting a new conversation, ask it to read `PROGRESS.md`, inspect the current
files and Git status, and continue verification before proposing Step 3.
