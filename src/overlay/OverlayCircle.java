package overlay;

import core.MouseInput;
import graphics.Graphics2D;
import util.Color4d;
import util.Vec2;

public class OverlayCircle extends OverlayItem {

    public Vec2 pos;
    public Vec2 size;
    public Color4d color;
    public boolean border;

    public OverlayCircle(Vec2 pos, Vec2 size, Color4d color, boolean border) {
        this.pos = pos;
        this.size = size;
        this.color = color;
        this.border = border;
    }

    @Override
    public void draw() {
        Graphics2D.fillEllipse(pos, size, color, 20);
        if (border) {
            Graphics2D.drawEllipse(pos, size, Color4d.BLACK, 20);
        }
    }

    @Override
    public boolean selected() {
        return MouseInput.mouse().subtract(pos).divide(size).lengthSquared() < 1;
    }
}
