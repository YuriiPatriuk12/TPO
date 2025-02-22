import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

class Ball {
    private Component canvas;
    private static final int SIZE = 20;
    private int x = 0;
    private int y = 0;
    private int dx = 2;
    private int dy = 2;

    public Ball(Component c) {
        this.canvas = c;
        if (Math.random() < 0.5) {
            x = new Random().nextInt(this.canvas.getWidth());
            y = 0;
        } else {
            x = 0;
            y = new Random().nextInt(this.canvas.getHeight());
        }
    }

    public static void f() {
        int a = 0;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.green);
        g2.fill(new Ellipse2D.Double(x, y, SIZE, SIZE));
    }

    public void move(ArrayList<Pocket> pockets) {
        x += dx;
        y += dy;
        if (x < 0) {
            x = 0;
            dx = -dx;
        }
        if (x + SIZE >= this.canvas.getWidth()) {
            x = this.canvas.getWidth() - SIZE;
            dx = -dx;
        }
        if (y < 0) {
            y = 0;
            dy = -dy;
        }
        if (y + SIZE >= this.canvas.getHeight()) {
            y = this.canvas.getHeight() - SIZE;
            dy = -dy;
        }

        for (Pocket p : pockets) {
            if(isCollided(p))
            {
                ((BallThread)Thread.currentThread()).interrupt();
                break;
            }
        }

        this.canvas.repaint();
    }

    private boolean isCollided(Pocket p) {
        int pocketX = p.getX();
        int pocketY = p.getY();

        double distance = Math.sqrt(Math.pow(x-pocketX,2) + Math.pow(y-pocketY,2));

        if(distance <= SIZE) {
            ((BallCanvas)canvas).removeBall(this);
            return true;
        }
        return false;
    }
}