public class CounterSynchronizedBlock implements ICounter {
    private int count;

    public void increment() {
        synchronized (this) {
            count++;
        }
    }

    public void decrement() {
        synchronized (this) {
            count--;
        }
    }

    public String getMessage() {return "Block synchronized counter result: " + count;}
}
