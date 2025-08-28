import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    enum Contention {
        LOW(thinkMs(/*add here*/), work(/*add here*/)),
        MEDIUM(thinkMs(/*add here*/), work(/*add here*/)),
        HIGH(thinkMs(/*add here*/), work(/*add here*/));

        final int thinkMs;
        final int csWorkIters;
        Contention(int thinkMs, int csWorkIters) { this.thinkMs = thinkMs; this.csWorkIters = csWorkIters; }

        static int thinkMs(int v) { return v; }
        static int work(int v) { return v; }
    }

    public static void main(String[] args) throws Exception {
        //TODO: Test your Code
    }
}
