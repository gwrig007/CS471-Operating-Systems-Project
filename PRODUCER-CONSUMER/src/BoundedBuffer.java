import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public final class BoundedBuffer {
    private final Queue<SalesRecord> queue;
    private final Semaphore emptySlots;
    private final Semaphore fullSlots;
    private final Semaphore mutex;

    public BoundedBuffer(int capacity) {
        this.queue = new ArrayDeque<>(capacity);
        this.emptySlots = new Semaphore(capacity);
        this.fullSlots = new Semaphore(0);
        this.mutex = new Semaphore(1);
    }

    public void put(SalesRecord record) throws InterruptedException {
        emptySlots.acquire();
        mutex.acquire();
        try {
            queue.add(record);
        } finally {
            mutex.release();
            fullSlots.release();
        }
    }

    public SalesRecord take() throws InterruptedException {
        fullSlots.acquire();
        mutex.acquire();
        try {
            return queue.remove();
        } finally {
            mutex.release();
            emptySlots.release();
        }
    }
}
