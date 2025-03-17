package Part_2;

import java.util.Random;

public class Producer implements Runnable {
    private final Drop drop;

    public Producer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        int SIZE = 100;
        int[] importantInfo = new int[SIZE];
        Random random = new Random();

        for (int i = 0; i < SIZE; i++) {
            importantInfo[i] = i;
        }

        for (int i = 0;
             i < importantInfo.length;
             i++) {
            drop.put(importantInfo[i]);
            System.out.println("Message sent: " + importantInfo[i]);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {}
        }
        drop.put(-1);
    }
}
