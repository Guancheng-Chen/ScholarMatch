# ScholarMatch Client — Semantic Scholar Integration

Unrelated to ScholarMatch's own server (see `API.md`) — this is a public API called directly by the client's `SemanticScholarGateway`, used to search authors by name and auto-import their paper list during registration/profile editing. Free, no API key required.

## GET https://api.semanticscholar.org/graph/v1/author/search

**Query parameters:** `query={name}&fields=authorId,name,affiliations,paperCount,hIndex,citationCount`

**Response excerpt:**
```json
{
  "data": [
    {
      "authorId": "string",
      "name": "string",
      "affiliations": ["string"],
      "paperCount": 0,
      "hIndex": 0,
      "citationCount": 0
    }
  ]
}
```

**Note: `hIndex` / `citationCount` can be missing or `null`.** The client code (`SemanticScholarGateway.searchAuthors(...)`) treats them as possibly absent: `node.has("hIndex") && !node.get("hIndex").isNull() ? ... : null`, mapped to `AuthorCandidateDataAccessInterface.getHIndex()` / `getCitationCount()`, both boxed `Integer` and nullable. This defensive handling exists in the code, but hasn't been confirmed against a real low-citation or newly registered author via Postman — searching `/author/search` for an obscure author with few papers and saving the raw response would confirm whether the "N/A instead of 0" design decision (already assumed in the blueprint) actually triggers on real data.

Up to 5 candidates are kept (`MAX_AUTHOR_CANDIDATES`), used to disambiguate authors sharing a name.

## GET https://api.semanticscholar.org/graph/v1/author/{authorId}/papers

**Query parameters:** `fields=title,year,citationCount,externalIds&limit=50`

**Response excerpt:**
```json
{
  "data": [
    {
      "title": "string",
      "year": 2023,
      "citationCount": 0,
      "externalIds": { "DOI": "string" }
    }
  ]
}
```
`externalIds.DOI` isn't always present — the client's `extractDoi(...)` returns an empty string when missing rather than dropping the paper.

Failure handling: a 404 is treated as "no data" and returns an empty list (no exception thrown); 429 and other 4xx/5xx responses throw `ExternalServiceException`, at which point the client's `FallbackUserApiGateway` switches to `LocalUserApiGateway`'s offline dataset instead of failing the search outright.
