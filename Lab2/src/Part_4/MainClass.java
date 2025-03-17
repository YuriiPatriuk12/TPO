package Part_4;

public class MainClass {
    public static void main(String[] args) {

        Thread pipeThread = new SymbolPrinter('|');
        Thread backslashThread = new SymbolPrinter('\\');
        Thread slashThread = new SymbolPrinter('/');

        System.out.println("None ordered");

        pipeThread.start();
        backslashThread.start();
        slashThread.start();

        try {
            pipeThread.join();
            backslashThread.join();
            slashThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        OrderedSymbolPrinter printer = new OrderedSymbolPrinter();

        pipeThread = new Thread(printer::printPipe);
        backslashThread = new Thread(printer::printBackslash);
        slashThread = new Thread(printer::printSlash);

        System.out.println("\nOrdered");

        pipeThread.start();
        backslashThread.start();
        slashThread.start();
        try {
            pipeThread.join();
            backslashThread.join();
            slashThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
