package Part_4;

public class SymbolPrinter extends Thread {
    private final char symbol;

    public SymbolPrinter(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public void run() {
        for (int i = 0; i < 90; i++) {
            System.out.print(symbol);
        }
        System.out.println();
    }
}