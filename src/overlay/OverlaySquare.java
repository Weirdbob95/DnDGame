package overlay;

import core.MouseInput;
import graphics.Graphics2D;
import grid.Square;
import util.Color4d;
import util.Vec2;

public class OverlaySquare extends OverlayItem {

    public Square square;
    public Color4d color;
    public Vec2 pos;
    public Vec2 size;

    public OverlaySquare(Square square, Color4d color) {
        this.square = square;
        this.color = color;
        pos = square.LL();
        size = new Vec2(Square.SIZE, Square.SIZE);
    }

    @Override
    public void draw() {
        Graphics2D.fillRect(pos, size, color);
    }

    @Override
    public boolean selected() {
        return MouseInput.mouse().containedBy(pos, pos.add(size));
    }
}
