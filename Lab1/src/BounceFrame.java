import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BounceFrame extends JFrame {
    private BallCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;
    private static final int EXAMPLE_BLUE_BALLS = 50;

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce program");
        this.canvas = new BallCanvas();
        System.out.println("In Frame Thread name = " + Thread.currentThread().getName());

        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        JButton buttonRed = new JButton("Add Red Ball");
        JButton buttonBlue = new JButton("Add Blue Ball");
        JButton buttonExample = new JButton("Example");
        JButton buttonStop = new JButton("Stop");

        buttonRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBall(Color.RED, Thread.MAX_PRIORITY);
            }
        });

        buttonBlue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBall(Color.BLUE, Thread.MIN_PRIORITY);
            }
        });

        buttonExample.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < EXAMPLE_BLUE_BALLS; i++)
                {
                    createBall(Color.BLUE, Thread.MIN_PRIORITY);
                }

                createBall(Color.RED, Thread.MAX_PRIORITY);
            }
        });

        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonPanel.add(buttonRed);
        buttonPanel.add(buttonBlue);
        buttonPanel.add(buttonExample);
        buttonPanel.add(buttonStop);
        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createBall(Color color, int priority)
    {
        Ball b = new Ball(canvas, color);
        canvas.add(b);
        BallThread thread = new BallThread(b);
        thread.setPriority(priority);
        thread.start();
        System.out.println("Thread name = " + thread.getName());
    }
}