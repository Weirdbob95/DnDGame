package grid;

import animations.MoveAnimation;
import core.AbstractComponent;
import creature.Creature;
import creature.CreatureDescriptionComponent;
import java.util.ArrayList;
import movement.PositionComponent;
import util.Vec2;

public class GridLocationComponent extends AbstractComponent {

    public Square lowerLeft;
    public ArrayList<Square> occupied;
    public Creature creature;
    public CreatureDescriptionComponent cdc;

    public GridLocationComponent(Square lowerLeft, Creature creature) {
        this.lowerLeft = lowerLeft;
        this.creature = creature;
        cdc = creature.cdc;
        int size = cdc.size.squares;
        occupied = new ArrayList();
        if (lowerLeft != null) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    Square s = World.grid.tileGrid[lowerLeft.x + i][lowerLeft.y + j];
                    if (s != null) {
                        occupied.add(s);
                        s.creature = creature;
                    }
                }
            }
        }
    }

    public Vec2 center() {
        int size = cdc.size.squares;
        return lowerLeft.LL().add(new Vec2(1, 1).multiply(size * .5 * Square.SIZE));
    }

    public void moveAlongPath(ArrayList<Square> path) {
        for (Square s : occupied) {
            if (s != null) {
                s.creature = null;
            }
        }
        occupied.clear();
        int size = cdc.size.squares;
        lowerLeft = path.get(path.size() - 1);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Square s = World.grid.tileGrid[lowerLeft.x + i][lowerLeft.y + j];
                if (s != null) {
                    occupied.add(s);
                    s.creature = creature;
                }
            }
        }
        for (Square s : path) {
            new MoveAnimation(creature, s.LL().add(new Vec2(1, 1).multiply(size * .5 * Square.SIZE))).start();
        }
    }

    public void moveToSquare(Vec2 pos, boolean animate) {
        for (Square s : occupied) {
            if (s != null) {
                s.creature = null;
            }
        }
        occupied.clear();
        int size = cdc.size.squares;
        lowerLeft = World.grid.tileAt(pos.subtract(new Vec2(size / 2 - .5, size / 2 - .5)));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Square s = World.grid.tileGrid[lowerLeft.x + i][lowerLeft.y + j];
                if (s != null) {
                    occupied.add(s);
                    s.creature = creature;
                }
            }
        }
        if (animate) {
            new MoveAnimation(creature, lowerLeft.LL().add(new Vec2(1, 1).multiply(size * .5 * Square.SIZE))).start();
        } else {
            creature.getComponent(PositionComponent.class).pos = center();
        }
    }
}
