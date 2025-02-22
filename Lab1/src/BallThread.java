import java.util.ArrayList;

public class BallThread extends Thread {
    private Ball b;
    private ArrayList<Pocket> pockets;

    public BallThread(Ball ball, ArrayList<Pocket> pockets) {
        b = ball; this.pockets = pockets;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i < 10000; i++) {
                b.move(pockets);
                System.out.println("Thread name = " + Thread.currentThread().getName());
                Thread.sleep(5);
            }
        } catch (InterruptedException ex) {
        }
    }
}