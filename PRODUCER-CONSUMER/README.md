# Producer-Consumer Simulation (CS471 Project)

This folder contains the full implementation for **Problem 2: Producer-Consumer**.

Language: **Java**

The simulation models:
- `p` producer threads (each producer is assigned one fixed store ID)
- `c` consumer threads
- a shared bounded buffer of size `b`
- exactly `1000` generated sales records total

Each sales record contains:
- sales date (`DD/MM/16`)
- store ID (`1..p`)
- register number (`1..6`)
- sale amount (`0.50..999.99`)

## Directory Layout

- `src/` Java source code
- `input/` sample configuration input files
- `output/` sample output files and the 9-run summary
- `build/` compiled `.class` files after running `javac`

## Compile and Run

Open terminal in this `PRODUCER-CONSUMER` directory and run:

```powershell
New-Item -ItemType Directory -Force -Path build | Out-Null
javac -d build src\*.java
```

### Single Run

```powershell
java -cp build Main <producers> <consumers> [bufferSize]
```

Example:

```powershell
java -cp build Main 5 10 20
```

### Required 9 Runs (p = 2,5,10 and c = 2,5,10)

```powershell
java -cp build Main --batch 20 output\summary-9-runs.csv
```

This executes all 9 combinations and writes a CSV summary to `output/summary-9-runs.csv`.

## Shared Variables and Synchronization

The implementation explicitly uses semaphores and shared variables:

### Semaphores

In `src/BoundedBuffer.java`:
- `emptySlots` (`Semaphore(capacity)`): tracks available empty buffer slots
- `fullSlots` (`Semaphore(0)`): tracks number of produced items available to consume
- `mutex` (`Semaphore(1)`): provides mutual exclusion around queue operations

These enforce correct producer-consumer synchronization on the shared queue.

### Shared Variables

In `src/ProductionController.java`:
- `producedCount` is shared across all producers
- `reserveNextRecord()` is `synchronized` to ensure the global production limit is exactly `1000`

In `src/GlobalStatistics.java`:
- shared global totals (`storeTotals`, `monthTotals`, `aggregateSales`)
- `addRecord(...)` is `synchronized` to prevent race conditions while consumers merge local results

### Completion Flag Mechanism

After all producer threads finish, the main thread inserts one `POISON_PILL` record per consumer.
Each consumer exits when it receives this sentinel value. This acts as the designated completion signal for consumption.

## Statistics Reported

Each run prints:
- each consumer's local statistics (`consumed count`, `local aggregate sales`)
- store-wide total sales
- month-wise total sales across all stores
- aggregate sales
- total simulation time

## Submitted Sample Artifacts

- `output/sample-run-p2-c2.txt` (single sample run output)
- `output/summary-9-runs.csv` (all 9 required combinations)
- `output/comparison-report.md` (time comparison across 9 runs)
- `input/experiment-config.txt` (sample run configuration definitions)

## Notes

- Producers sleep for random `5-40 ms` between generated records.
- Random record generation follows project constraints for date, store ID, register number, and amount.
- Buffer size defaults to `20` if omitted.