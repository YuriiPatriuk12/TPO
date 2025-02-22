import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Pocket {
    private Component canvas;
    public static final int SIZE = 25;
    private int x = 0;
    private int y = 0;

    public Pocket(Component c, int x, int y) {
        this.canvas = c;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.fill(new Ellipse2D.Double(x, y, SIZE, SIZE));
    }

    public int getX() {return x;}
    public int getY() {return y;}
}
