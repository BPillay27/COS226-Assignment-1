
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class TTASLock implements Lock {
    private final AtomicBoolean state = new AtomicBoolean(false);
    private static final int MIN_DELAY = 0; // in microseconds
    private static final int MAX_DELAY = 0; // max backoff delay (µs)

    // Counter to track how many times backoff (sleep) occurred
    public final AtomicInteger backoffCount = new AtomicInteger(0);

    @Override
    public void lock() {
        // done
        final int max=Math.max(0, MAX_DELAY);
        int delay=Math.max(0,MIN_DELAY);

        for(;;){
            while (state.get()){
                delay=backoff(delay,max);
            }

            if(!state.getAndSet(true)){
                return;
            }

            delay=backoff(delay, max);
        }
    }

    @Override
    public void unlock() {
        // done
        state.set(false);
    }

    private int backoff(int currentDelay,int maxDelay){
        if(maxDelay<=0) return currentDelay;

        int cap = (currentDelay <=0) ? 1: Math.min(currentDelay,maxDelay);
        int sleepMicros=ThreadLocalRandom.current().nextInt(1,cap+1);
        if(sleepMicros>0){
            backoffCount.incrementAndGet();
        }

        long next=(currentDelay<=0) ? 1L : (long) currentDelay*2L;
        return (int) Math.min(next,(long) maxDelay);
    }
}