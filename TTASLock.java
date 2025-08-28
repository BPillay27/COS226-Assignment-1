
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class TTASLock implements Lock {
    private final AtomicBoolean state = new AtomicBoolean(false);
    private static final int MIN_DELAY =    // in microseconds
    private static final int MAX_DELAY =   // max backoff delay (µs)

    // Counter to track how many times backoff (sleep) occurred
    public final AtomicInteger backoffCount = new AtomicInteger(0);

    public void lock() {
        //TODO: Implement Function
    }

    public void unlock() {
        //TODO: Implement Function
    }
}