import java.util.concurrent.locks.Lock;

public class MainClass {
    public static void main(String[] args) {
        Counter counter = new Counter();
        startCounter(counter);

        CounterSynchronized counterSynchronized = new CounterSynchronized();
        startCounter(counterSynchronized);

        CounterSynchronizedBlock counterSynchronizedBlock = new CounterSynchronizedBlock();
        startCounter(counterSynchronizedBlock);

        LockCounter lockCounter = new LockCounter();
        startCounter(lockCounter);
    }

    private static void startCounter(ICounter counter) {
        new Thread(() -> {
            for(int i = 0; i < 100000; i++) {
                counter.increment();
            }
        }).start();

        new Thread(() -> {
            for(int i = 0; i < 100000; i++) {
                counter.decrement();
            }
        }).start();

        System.out.println(counter.getMessage());
    }
}
