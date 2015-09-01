package grid;

import animations.MoveAnimation;
import core.AbstractComponent;
import creature.Creature;
import java.util.ArrayList;
import movement.PositionComponent;
import util.Vec2;

public class GridLocationComponent extends AbstractComponent {

    public Square lowerLeft;
    public ArrayList<Square> occupied;
    public Creature creature;

    public GridLocationComponent(Square lowerLeft, Creature creature) {
        this.lowerLeft = lowerLeft;
        this.creature = creature;
        occupied = new ArrayList();
        if (lowerLeft != null) {
            moveToSquare(lowerLeft);
        }
    }

    public Vec2 center() {
        return lowerLeft.LL().add(new Vec2(1, 1).multiply(size() * .5 * Square.SIZE));
    }

    public Vec2 centerAt(Square pos) {
        return pos.LL().add(new Vec2(1, 1).multiply(size() * .5 * Square.SIZE));
    }

    public void moveAlongPath(ArrayList<Square> path) {
        occupied.forEach(s -> s.creature = null);
        lowerLeft = path.get(path.size() - 1);
        occupied = occupiedAt(lowerLeft);
        occupied.forEach(s -> s.creature = creature);
        path.forEach(s -> new MoveAnimation(creature, s.LL().add(new Vec2(1, 1).multiply(size() * .5 * Square.SIZE))).start());
    }

    public void moveToSquare(Square lowerLeft) {
        occupied.forEach(s -> s.creature = null);
        this.lowerLeft = lowerLeft;
        occupied = occupiedAt(lowerLeft);
        occupied.forEach(s -> s.creature = creature);
    }

    public ArrayList<Square> occupiedAt(Square pos) {
        return GridUtils.area(pos, size());
    }

    public int size() {
        return creature.cdc.size.squares;
    }

    public void updateSpritePos() {
        creature.getComponent(PositionComponent.class).pos = center();
    }
}
