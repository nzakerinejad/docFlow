# docFlow backend

Java 21 and Spring Boot REST API. Maven Wrapper is included.

## Run locally

```bash
cd backend
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk env
./mvnw spring-boot:run
```

`.sdkmanrc` selects the existing SDKMAN Java 21 installation. If needed, run `sdk env install` first.

## Search

```bash
curl 'http://localhost:8080/api/search?query=onboarding&sources=CONFLUENCE,SLACK,GIT'
```

`query` is required, must contain non-whitespace text, and is limited to 1,000 characters.
`sources` is optional; omit it to search all connected platforms. Supported values:
`CONFLUENCE`, `SLACK`, `GIT`, `JIRA`, `FILE_STORAGE` (uppercase).
Invalid input returns HTTP 400.

```json
{"query":"onboarding","connectedSources":[],"results":[]}
```

This first step defines the API and connector interface. No platforms are connected yet,
so results and connectedSources are empty. Each result will contain `id`, `source`,
`title`, `snippet`, and `url`. Add Spring beans implementing `SearchConnector` to
integrate platforms. Connectors must enforce the caller's source permissions;
authentication and real platform integrations are still to be implemented.

## Verify

```bash
./mvnw test
```
