package animations;

import creature.Creature;
import grid.GridLocationComponent;
import grid.Tile;
import util.Vec2;

public class MoveAnimation extends Animation {

    public GridLocationComponent glc;
    public Vec2 speed;

    public MoveAnimation(Creature c, int x, int y) {
        glc = c.getComponent(GridLocationComponent.class);
        glc.x = x;
        glc.y = y;
        speed = Tile.positionAt(x, y).subtract(glc.pos).setLength(4);
        glc.rot = speed.direction();
    }

    @Override
    public boolean update() {
        if (glc.pos.subtract(Tile.positionAt(glc.x, glc.y)).lengthSquared() < speed.lengthSquared()) {
            glc.pos = Tile.positionAt(glc.x, glc.y);
            return true;
        }
        glc.pos = glc.pos.add(speed);
        return false;
    }

}
