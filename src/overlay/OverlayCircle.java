package overlay;

import core.MouseInput;
import graphics.Graphics2D;
import util.Color4d;
import util.Vec2;

public class OverlayCircle extends OverlayItem {

    public Color4d color;
    public Vec2 pos;
    public Vec2 size;

    public OverlayCircle(Vec2 pos, Vec2 size, Color4d color) {
        this.pos = pos;
        this.size = size;
        this.color = color;
    }

    @Override
    public void draw() {
        Graphics2D.fillEllipse(pos, size, color, 20);
    }

    @Override
    public boolean selected() {
        return MouseInput.mouse().subtract(pos).divide(size).lengthSquared() < 1;
    }
}
