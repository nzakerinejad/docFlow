import { useState } from 'react'
import type { FormEvent } from 'react'
import './App.css'

function App() {
  const [query, setQuery] = useState('')
  const [error, setError] = useState('')
  const [submittedQuery, setSubmittedQuery] = useState('')

  const [response, setResponse] = useState<SearchResponse | null>(null)
  const [loading, setLoading] = useState(false)

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()
    if (loading) return
    setResponse(null)
    setSubmittedQuery('')
    if (!query.trim()) {
      setError('Enter a search query to continue.')
      return
    }
    setError('')
    setSubmittedQuery(query.trim())
    setLoading(true)
    try {
      const result = await fetch(`/api/search?${new URLSearchParams({ query: query.trim() })}`, {
        signal: AbortSignal.timeout(30000),
      })
      if (!result.ok) throw new Error(result.status === 400
        ? 'Enter a search query between 1 and 1,000 characters.'
        : 'Search is unavailable. Check that the backend is running and try again.')
      const data: unknown = await result.json()
      if (!isSearchResponse(data)) throw new Error('Search returned an unexpected response. Please try again.')
      setResponse(data)
    } catch (cause) {
      setError(cause instanceof Error && cause.name === 'Error'
        ? cause.message
        : 'Could not reach search. Check your connection and try again.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <main className="search-page">
      <header>
        <p className="brand">docFlow</p>
        <h1>Your company knowledge, in one place</h1>
        <p>Find documents, conversations, and assets across your company’s tools.</p>
      </header>
      <form className="search-form" role="search" onSubmit={handleSubmit} noValidate>
        <label htmlFor="search-query">Search company knowledge</label>
        <div className="search-controls">
          <input
            id="search-query"
            type="search"
            value={query}
            onChange={(event) => {
              setQuery(event.target.value)
              setResponse(null)
              setError('')
              setSubmittedQuery('')
            }}
            placeholder="e.g. onboarding guide"
            maxLength={1000}
            required
            disabled={loading}
            aria-invalid={Boolean(error)}
            aria-describedby={error ? 'search-error' : undefined}
          />
          <button type="submit" disabled={loading}>{loading ? 'Searching…' : 'Search'}</button>
        </div>
        {error && <p id="search-error" className="search-error" role="alert">{error}</p>}
      </form>
      <section aria-label="Search results" aria-busy={loading}>
        <p className="search-status" role="status">
          {loading ? 'Searching company knowledge…' : response && (
            response.connectedSources.length === 0
              ? 'No company sources are connected yet. Results will appear once sources are connected.'
              : response.results.length === 0
                ? `No results found for “${submittedQuery}”. Try a different query.`
                : `${response.results.length} results for “${submittedQuery}”.`
          )}
        </p>
        {response && response.connectedSources.length > 0 && (
          <p className="search-status">Connected sources: {response.connectedSources.join(', ')}</p>
        )}
        <ul className="result-list">
          {response?.results.map((result) => (
            <li key={`${result.source}:${result.id}`} className="result-card">
              <p className="result-source">{result.source}</p>
              <h2>{safeResultUrl(result.url)
                ? <a href={result.url} target="_blank" rel="noopener noreferrer">{result.title}</a>
                : result.title}</h2>
              <p>{result.snippet}</p>
            </li>
          ))}
        </ul>
      </section>
    </main>
  )
}

export default App


type SearchResult = { id: string; source: string; title: string; snippet: string; url: string }
type SearchResponse = { query: string; connectedSources: string[]; results: SearchResult[] }

function isSearchResponse(value: unknown): value is SearchResponse {
  if (!value || typeof value !== 'object') return false
  const data = value as SearchResponse
  return typeof data.query === 'string'
    && Array.isArray(data.connectedSources)
    && data.connectedSources.every((source) => typeof source === 'string')
    && Array.isArray(data.results)
    && data.results.every((result) => result && ['id', 'source', 'title', 'snippet', 'url']
      .every((key) => typeof result[key as keyof SearchResult] === 'string'))
}

function safeResultUrl(value: string) {
  try {
    return ['https:', 'http:'].includes(new URL(value).protocol)
  } catch {
    return false
  }
}
