import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BallCanvas extends JPanel {
    private final ArrayList<Ball> balls = new ArrayList<>();
    private final ArrayList<Pocket> pockets = new ArrayList<>();
    private final BounceFrame bounceFrame;

    public BallCanvas(BounceFrame bounceFrame) {
        this.bounceFrame = bounceFrame;
    }

    public void add(Pocket pocket) {
        this.pockets.add(pocket);
    }

    public void add(Ball b) {
        this.balls.add(b);
    }

    public void removeBall(Ball b) {
        balls.remove(b);
        bounceFrame.incrementScore();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (Ball b : balls) {
            b.draw(g2);
        }

        for (Pocket p : pockets) {
            p.draw(g2);
        }
    }
}