import java.util.Arrays;

public final class GlobalStatistics {
    private final double[] storeTotals;
    private final double[] monthTotals;
    private double aggregateSales;

    public GlobalStatistics(int producerCount) {
        this.storeTotals = new double[producerCount + 1];
        this.monthTotals = new double[13];
        this.aggregateSales = 0.0;
    }

    public synchronized void addRecord(SalesRecord record) {
        double amount = record.getSaleAmount();
        storeTotals[record.getStoreId()] += amount;
        monthTotals[record.getMonth()] += amount;
        aggregateSales += amount;
    }

    public synchronized Snapshot snapshot() {
        return new Snapshot(
                Arrays.copyOf(storeTotals, storeTotals.length),
                Arrays.copyOf(monthTotals, monthTotals.length),
                aggregateSales
        );
    }

    public static final class Snapshot {
        private final double[] storeTotals;
        private final double[] monthTotals;
        private final double aggregateSales;

        private Snapshot(double[] storeTotals, double[] monthTotals, double aggregateSales) {
            this.storeTotals = storeTotals;
            this.monthTotals = monthTotals;
            this.aggregateSales = aggregateSales;
        }

        public double[] getStoreTotals() {
            return storeTotals;
        }

        public double[] getMonthTotals() {
            return monthTotals;
        }

        public double getAggregateSales() {
            return aggregateSales;
        }
    }
}
