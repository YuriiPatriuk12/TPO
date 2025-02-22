import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockCounter implements ICounter {
    private int count;
    private final Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            count++;
        }
        finally {
            lock.unlock();
        }
    }

    public void decrement() {
        lock.lock();
        try{
            count--;
        }
        finally {
            lock.unlock();
        }
    }

    public String getMessage() {return "Synchronized counter result: " + count;}
}
