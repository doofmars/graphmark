# GraphMark

Benchmark of graph DB patterns in Janusgraph

Reference test
- Simulated super node with many edges
- Target query find sink with certain edge

```
+--------+      +------+
|        |      |      |
| Source +----->+ Sink |
| (n=1)  |(n=1m)|(n=1m)|
+--------+      +------+
```

Mediator
- Single edge to every mediator
- Edges to sink distributed evenly
- Target query find sink with certain edge (mediator -> sink)

```
                     +------------+
                     |            |
                 +---> Mediator 1 +---+
                 |   | (n=1)      |   |
                 |   +------------+   |
                 |                    |
                 |   +------------+   |
                 |   |            |   |
                 +---> Mediator 2 +---+
+--------+       |   | (n=1)      |   |        +------+
|        |       |   +------------+   |        |      |
| Source +-------+                    +--------> Sink |
| (n=1)  | (n=1) |   +------------+   | (n=1m) |(n=1m)|
+--------+       |   |            |   |        +------+
                 +---> Mediator 3 +---+
                 |   | (n=1)      |   |
                 |   +------------+   |
                 |                    |
                 |   +------------+   |
                 |   |            |   |
                 +---> Mediator n +---+
                     | (n=1)      |
                     +------------+
```