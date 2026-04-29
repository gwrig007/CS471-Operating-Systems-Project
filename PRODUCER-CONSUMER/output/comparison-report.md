# Producer-Consumer 9-Run Time Comparison

The simulation was executed for all required combinations:
- Producers (`p`): 2, 5, 10
- Consumers (`c`): 2, 5, 10
- Buffer size (`b`): 20
- Total records generated: 1000

## Runtime Summary

| p | c | elapsed time (ms) |
|---|---|-------------------|
| 2 | 2 | 11880 |
| 2 | 5 | 11399 |
| 2 | 10 | 11564 |
| 5 | 2 | 4579 |
| 5 | 5 | 4690 |
| 5 | 10 | 4676 |
| 10 | 2 | 2336 |
| 10 | 5 | 2395 |
| 10 | 10 | 2271 |

## Observations

1. Increasing producer count had the strongest impact on completion time.
2. Runs with `p=10` finished fastest (about 2.3 seconds), while runs with `p=2` took about 11.6-11.7 seconds.
3. For a fixed producer count, changing consumers from 2 to 10 had smaller impact than changing producer count.
4. Since each producer sleeps randomly between 5-40 ms, producer parallelism dominates how quickly 1000 records are generated.

## Best and Worst Cases

- Fastest run: `p=10, c=10` at `2271 ms`
- Slowest run: `p=2, c=2` at `11880 ms`

Speedup from slowest to fastest is approximately `11880 / 2271 = 5.23x`.

## Source Files

The full numeric summary is available in:
- `output/summary-9-runs.csv`

A sample console output run is available in:
- `output/sample-run-p2-c2.txt`
