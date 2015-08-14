package overlay;

import graphics.Graphics2D;
import util.Color4d;
import util.Vec2;

public class OverlayLine extends OverlayItem {

    public Vec2 p1, p2;
    public Color4d color;
    public int width;

    public OverlayLine(Vec2 p1, Vec2 p2, Color4d color, int width) {
        this.p1 = p1;
        this.p2 = p2;
        this.color = color;
        this.width = width;
    }

    @Override
    public void draw() {
        Graphics2D.drawLine(p1, p2, color, width);
    }

    @Override
    public boolean selected() {
        return false;
    }
}
