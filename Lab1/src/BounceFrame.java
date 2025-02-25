import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BounceFrame extends JFrame {
    private BallCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;
    private int score = 0;

    JButton buttonStart = new JButton("Start");
    JButton buttonStop = new JButton("Stop");
    JLabel scoreLabel = new JLabel("Score: " + score);

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce program");
        this.canvas = new BallCanvas(this);
        System.out.println("In Frame Thread name = " + Thread.currentThread().getName());

        Container content = this.getContentPane();

        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(Color.LIGHT_GRAY);
        scorePanel.add(scoreLabel);
        content.add(scorePanel, BorderLayout.NORTH);

        content.add(this.canvas, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);

        ArrayList<Pocket> pockets = new ArrayList<>();

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ball b = new Ball(canvas);
                canvas.add(b);
                BallThread thread = new BallThread(b, pockets);
                thread.start();
                System.out.println("Thread name = " + thread.getName());
            }
        });

        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);
        content.add(buttonPanel, BorderLayout.SOUTH);

        this.setVisible(true);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        pockets.add(new Pocket(canvas, 0,0));
        pockets.add(new Pocket(canvas, 0,canvasHeight-Pocket.SIZE));
        pockets.add(new Pocket(canvas, canvasWidth-Pocket.SIZE,0));
        pockets.add(new Pocket(canvas, canvasWidth-Pocket.SIZE,canvasHeight-Pocket.SIZE));

        for (Pocket p : pockets) {
            canvas.add(p);
        }
    }

    public synchronized void incrementScore(){
        score = score + 1;
        scoreLabel.setText("Score: " + score);
    }
}