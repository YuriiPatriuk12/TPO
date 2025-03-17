package Part_4;

public class OrderedSymbolPrinter {
    private int state = 0;  
    private final int PRINT_COUNT = 90;

    public synchronized void printPipe() {
        for (int i = 0; i < PRINT_COUNT; i++) {
            while (state != 0) {
                waitForTurn();
            }
            System.out.print('|');
            state = 1;
            notifyAll();
        }
        System.out.println();
    }

    public synchronized void printBackslash() {
        for (int i = 0; i < PRINT_COUNT; i++) {
            while (state != 1) {
                waitForTurn();
            }
            System.out.print('\\');
            state = 2;
            notifyAll();
        }
    }

    public synchronized void printSlash() {
        for (int i = 0; i < PRINT_COUNT; i++) {
            while (state != 2) {
                waitForTurn();
            }
            System.out.print('/');
            state = 0;
            notifyAll();
        }
    }

    private void waitForTurn() {
        try {
            wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
