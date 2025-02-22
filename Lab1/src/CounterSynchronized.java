public class CounterSynchronized implements ICounter {
    private int count;

    public synchronized void increment() {count++;}
    public synchronized void decrement() {count--;}
    public String getMessage() {return "Synchronized counter result: " + count;}
}
