public class Counter implements ICounter {
    private int count;

    public void increment() {count++;}
    public void decrement() {count--;}
    public String getMessage() {return "Counter result: " + count;}
}
