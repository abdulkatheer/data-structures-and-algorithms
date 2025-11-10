# Bridges vs Articulation Point

## Bridges

when there's an egde (u,v), if we remove it, can v or v's descendants reach u or u's ancestors.
If it can't, then (u,v) is a bridge.

lowDisc(v) < disc(u) -> v or its descendants have a back edge to u's ancestors
lowDisc(v) == disc(u) -> v or its descendants have a back edge to u itself
lowDisc(v) > disc(u) -> v or its descendants do not have a back edge to u or u's ancestors

Bridge is, when the edge is the only connecting point between component u and component v.
if we remove that, u and v will be disconnected!
That's why we check for lowDisc(v) > disc(u)

## Articulation point

When there's an edge (u,v), if we remove u, can v or v's descendants reach ancestor of u.
If can't, u is an articulation point.

lowDisc(v) < disc(u) -> v or its descendants can reach u's ancestors
lowDisc(v) == disc(u) -> v or its descendants can't reach u's ancestors (but reach u itself)
lowDisc(v) > disc(u) -> v or its descendants can't reach u's ancestors

That's why we check for lowDisc(v) >= disc(u)

### Non-root node

lowDisc(v) >= disc(u) -> v or its descendants can't reach u's ancestors

### Root node

Why non-root node's condition doesn't work here?
If a root node has only one child, like 0 -> 1
From 1's perspective, 0 is an articulation point, but 0 doesn't have other components to get split.
So if a root node has more than 1 child, they're articulation points and not otherwise
