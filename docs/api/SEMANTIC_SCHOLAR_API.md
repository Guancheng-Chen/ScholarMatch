# ScholarMatch Client — Semantic Scholar Integration

Unrelated to ScholarMatch's own server (see `API.md`) — this is a public API called directly by the client's `SemanticScholarGateway`, used to search authors by name and auto-import their paper list during registration/profile editing.

Most endpoints work without an API key, but anonymous callers share a rate limit and can receive HTTP 429 responses. For more reliable development and demos, set an optional API key through the environment:

```text
SEMANTIC_SCHOLAR_API_KEY=your-key
```

When configured, `SemanticScholarGateway` sends it in the `x-api-key` header. Never commit the key to this repository.

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

The gateway requests up to 200 results so common names have enough candidates for local ranking. The interactor moves candidates with the same normalized name tokens to the front, orders those matches by citation count, and returns at most 20 candidates to the view. A numeric query is treated as a Semantic Scholar author ID, providing a fallback when name search cannot find the correct profile.

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
`externalIds.DOI` isn't always present — the client's `readDoi(...)` returns an empty string when missing rather than dropping the paper.

Failure handling: a 404 is treated as "no data" and returns an empty list (no exception thrown). A 429 is retried once after one second; if the retry also fails, or another 4xx/5xx response is returned, `SemanticScholarGateway` throws `ExternalServiceException`.
