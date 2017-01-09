package circles;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Circle {

    int y;
    int x;
    int r;
    Color c;
    ArrayList<Circle> circles;

    boolean growing = true;

    public Circle(int x, int y, Color color, ArrayList<Circle> circles) {
        this.x = x;
        this.y = y;
        this.c = color;
        r = 1;
        this.circles = circles;
    }

    private void render(Graphics g) {
        g.setColor(c);
        g.fillOval(x - r, y - r, r * 2, r * 2);
        /*g.setColor(Color.BLACK);
        g.drawOval(x - r, y - r, r * 2, r * 2);*/
    }

    private void grow() {
        if (growing) {
            r = r + 1;
        }
    }

    private boolean edges() {
        return (x - r < 0 || x + r > Panel.Width || y - r < 0 || y + r > Panel.Height);
    }

    public boolean inside(int x, int y) {
        float distanceX = this.x - x;
        float distanceY = this.y - y;
        return distanceX * distanceX + distanceY * distanceY <= this.r * this.r;
    }

    private boolean intersects(Circle circle) {
        float distanceX = this.x - circle.x;
        float distanceY = this.y - circle.y;
        float radiusSum = this.r + circle.r;
        return distanceX * distanceX + distanceY * distanceY <= radiusSum * radiusSum;
    }

    public void update(Graphics g) {

        render(g);
        if (growing) {
            grow();
            if (edges()) {
                growing = false;
            }
            for (Circle c : circles) {
                if (c != this) {
                    if (intersects(c)) {
                        growing = false;
                    }
                }
            }
        }
    }

}
