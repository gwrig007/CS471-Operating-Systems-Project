import java.util.concurrent.ThreadLocalRandom;

public final class Producer implements Runnable {
    private final int producerId;
    private final int storeId;
    private final ProductionController controller;
    private final BoundedBuffer buffer;

    public Producer(int producerId, int storeId, ProductionController controller, BoundedBuffer buffer) {
        this.producerId = producerId;
        this.storeId = storeId;
        this.controller = controller;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        try {
            while (controller.reserveNextRecord()) {
                int day = random.nextInt(1, 31);
                int month = random.nextInt(1, 13);
                int registerNumber = random.nextInt(1, 7);
                float saleAmount = (float) Math.round(random.nextDouble(0.50, 999.99) * 100.0) / 100.0f;

                SalesRecord record = new SalesRecord(day, month, storeId, registerNumber, saleAmount);
                buffer.put(record);

                long pauseMillis = random.nextLong(5, 41);
                Thread.sleep(pauseMillis);
            }
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            System.err.println("Producer " + producerId + " interrupted.");
        }
    }
}
