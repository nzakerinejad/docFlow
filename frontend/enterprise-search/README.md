# Enterprise Search frontend

React and TypeScript application built with Vite, configured for Cloudflare Workers with Static Assets.

The project currently contains the generated starter UI and a sample Worker API. Search, authentication, and enterprise integrations are planned; they are not implemented yet.

## Local development

Use Node.js 24 (verified with Node.js 24.22.0 and npm 11.19.1). If you use nvm:

```bash
nvm install 24
nvm use 24
```

From the repository root:

```bash
cd frontend/enterprise-search
npm ci
npm run dev
```

Open the local URL printed by Vite.

## Commands

Run these commands from `frontend/enterprise-search`:

| Command | Purpose |
| --- | --- |
| `npm run dev` | Start the development server |
| `npm run build` | Check TypeScript and create the production build |
| `npm run lint` | Run Oxlint |
| `npm run preview` | Build and preview the production application locally |
| `npm run cf-typegen` | Regenerate Worker binding types after configuration changes |
| `npm run deploy` | Build and deploy to Cloudflare Workers |

## Project layout

- `src/`: React UI, styles, and assets.
- `public/`: Static assets.
- `worker/index.ts`: Sample Worker handler; `/api/` paths return a sample JSON response.
- `wrangler.jsonc`: Cloudflare Worker and static asset configuration.
- `worker-configuration.d.ts`: Generated Worker binding types.

## Deployment

Authenticate with your Cloudflare account, then deploy:

```bash
npx wrangler login
npm run deploy
```

Deployment publishes the application to Cloudflare. The configured Worker name is `enterprise-search`.

Keep API secrets and integration credentials on the server. Local `.env*` and `.dev.vars*` files are ignored by Git, except example files.
