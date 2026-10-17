interface SearchEnv extends Env {
  BACKEND_URL?: string
}

export default {
  async fetch(request, env) {
    const url = new URL(request.url)
    if (url.pathname !== '/api/search') return new Response(null, { status: 404 })
    if (request.method !== 'GET') return new Response(null, { status: 405, headers: { Allow: 'GET' } })
    if (!env.BACKEND_URL) {
      return Response.json({ error: 'Search backend is not configured.' }, { status: 503 })
    }
    try {
      const target = new URL('/api/search', env.BACKEND_URL)
      target.search = url.search
      const response = await fetch(target, { signal: AbortSignal.timeout(25000), redirect: 'manual' })
      if (response.status >= 300 && response.status < 400) {
        console.error('Search backend returned an unexpected redirect:', response.status)
        return Response.json({ error: 'Search backend returned an unexpected redirect.' }, { status: 502 })
      }
      return new Response(response.body, {
        status: response.status,
        headers: {
          'Content-Type': response.headers.get('Content-Type') ?? 'application/json',
          'Cache-Control': 'no-store',
        },
      })
    } catch (error) {
      console.error('Search backend request failed:', error)
      return Response.json({ error: 'Search backend is unavailable.' }, { status: 502 })
    }
  },
} satisfies ExportedHandler<SearchEnv>
