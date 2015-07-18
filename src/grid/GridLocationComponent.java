package grid;

import core.AbstractComponent;
import util.Vec2;

public class GridLocationComponent extends AbstractComponent {

    public int x;
    public int y;
    public double rot;
    public Vec2 pos;

    public GridLocationComponent(int x, int y) {
        this.x = x;
        this.y = y;
        pos = Tile.positionAt(x, y);
    }
}
