# docFlow
# AI Enterprise Search

## 1. Overview

AI Enterprise Search is an AI-powered tool that enables employees to search and discover information across their organization's internal platforms through a unified interface.

The tool connects to enterprise services such as Slack, Confluence, Jira, Git repositories, and document storage systems, allowing users to retrieve relevant information without manually searching each platform.

The system uses AI to understand natural-language queries, retrieve relevant information from multiple sources, and optionally synthesize the results into answers supported by references to the original content.

## 2. Problem Statement

Company knowledge is distributed across multiple platforms:

- **Slack:** Conversations, discussions, shared files, and informal decisions.
- **Confluence:** Documentation, technical specifications, and internal knowledge.
- **Jira:** Tasks, issues, requirements, and project history.
- **Git platforms:** Source code, pull requests, commit history, and technical discussions.
- **File storage:** Documents, presentations, spreadsheets, and other company assets.

Finding information often requires employees to search each platform independently.

This creates several problems:

- Time wasted switching between platforms.
- Difficulty discovering information when its location is unknown.
- Relevant information missed because it exists in another system.
- Knowledge fragmented across conversations, documents, and project artifacts.
- Difficulty reconstructing the context and reasoning behind past decisions.

## 3. Project Goals

The system should:

1. Provide a single interface for searching across company platforms.
2. Support natural-language queries.
3. Retrieve relevant information from multiple connected sources.
4. Present results with references and links to their original locations.
5. Generate AI-assisted answers grounded in retrieved information.
6. Respect the original access permissions of each source.
7. Support adding new platform connectors without major architectural changes.

## 4. Initial Integrations

The initial target platforms are:

| Platform | Searchable Content |
|---|---|
| Slack | Messages, threads, shared files |
| Confluence | Pages and documentation |
| Jira | Issues, descriptions, comments |
| GitHub / GitLab | Repositories, code, issues, pull requests |
| File storage | Documents, PDFs, spreadsheets |

The exact platforms and integration order will be determined during planning.

## 5. Core Functional Requirements

### FR-001: Unified Search

Users can submit a search query through a single interface and retrieve results from multiple connected sources.

### FR-002: Natural-Language Queries

Users can describe what they are looking for in natural language instead of using platform-specific search syntax.

### FR-003: Source Attribution

Search results and generated answers must reference their original sources, including links where available.

### FR-004: AI-Assisted Answers

The system should be able to summarize and synthesize retrieved information into answers grounded in source material.

### FR-005: Extensible Connectors

The system must support integrating additional enterprise platforms through a modular connector architecture.

### FR-006: Access Control

Search results must respect the user's permissions in the original platform and must not expose unauthorized information.

## 6. Non-Goals (Initial Version)

- Replacing Slack, Jira, Confluence, or Git platforms.
- Editing or deleting content in connected systems.
- Automatically making business decisions.
- Training custom foundation models on company data.

## 7. Architecture

To be determined.

Key areas to investigate:

- Federated search versus centralized indexing.
- Connector interfaces and authentication.
- Semantic, keyword, and hybrid retrieval.
- AI orchestration and answer generation.
- Identity mapping and permission enforcement.
- Data synchronization, freshness, and deletion handling.

## 8. Implementation Roadmap

### Phase 1: Proof of Concept

- [ ] Define initial use cases.
- [ ] Choose the first data source.
- [ ] Implement a basic connector.
- [ ] Implement natural-language search.
- [ ] Return results with source references.

### Phase 2: Multi-Source Search

- [ ] Add additional connectors.
- [ ] Implement unified result ranking.
- [ ] Add AI-generated answers.
- [ ] Add source attribution.
- [ ] Implement an initial user interface.

### Phase 3: Enterprise Readiness

- [ ] Implement production authentication and authorization.
- [ ] Validate source-level permission enforcement.
- [ ] Add observability, monitoring, and auditing.
- [ ] Handle rate limits, retries, and connector failures.
- [ ] Add synchronization and data freshness mechanisms where required.
- [ ] Evaluate retrieval and answer quality.

## 9. Open Questions

- Should the system search external services in real time, maintain its own index, or use a hybrid approach?
- Which connector should be implemented first?
- Should the initial version provide search results, AI-generated answers, or both?
- How should users authenticate and authorize access to connected platforms?
- Should the tool be a standalone web application, an API, or an MCP server?
- What are the deployment and data residency requirements?

## 10. Project Status

**Stage:** Requirements and architecture planning; React + TypeScript frontend starter initialized

**Next Step:** Define the MVP and choose the initial search architecture.

## Frontend

The application is in [`frontend/enterprise-search`](frontend/enterprise-search). It currently contains the Cloudflare React starter UI and a sample Worker API; search features are not implemented yet.

### Local Development

Use Node.js 24 (verified with Node.js 24.22.0 and npm 11.19.1).

```bash
cd frontend/enterprise-search
npm ci
npm run dev
```

See the [frontend README](frontend/enterprise-search/README.md) for nvm setup, build and lint commands, project structure, and deployment instructions.

### Technology Stack

- **Framework:** React
- **Language:** TypeScript
- **Build Tool:** Vite
- **Hosting:** Cloudflare Workers with Static Assets
- **Deployment:** Cloudflare Wrangler
- **Backend:** Separate AI Search API (initially planned with Python/FastAPI)

### Functional Requirements

- Unified natural-language search interface
- AI chat with streaming responses
- Search results across Slack, Jira, Confluence, and Git
- Source citations and links
- Filters by platform and content type
- User authentication
- Responsive design

### Deployment

The React application will be built and deployed to Cloudflare.

The frontend will communicate with a separate backend responsible for search, AI orchestration, and enterprise integrations.

All integration credentials, API secrets, and authorization checks must remain on the server side.

### Implementation Tasks

- [x] Initialize React + TypeScript using Cloudflare
- [ ] Implement the main search interface
- [ ] Implement search results
- [ ] Implement AI response streaming
- [ ] Implement authentication
- [ ] Connect the frontend to the search API
- [ ] Deploy to Cloudflare
- [ ] Configure custom domain
- [ ] Set up CI/CD
