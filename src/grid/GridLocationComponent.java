package grid;

import core.AbstractComponent;
import creature.CreatureDescriptionComponent;
import java.util.ArrayList;
import util.Vec2;

public class GridLocationComponent extends AbstractComponent {

    public Square lowerLeft;
    public ArrayList<Square> occupied;
    public CreatureDescriptionComponent cdc;

    public GridLocationComponent(Square lowerLeft, CreatureDescriptionComponent cdc) {
        this.lowerLeft = lowerLeft;
        this.cdc = cdc;
        int size = cdc.size.squares;
        occupied = new ArrayList();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                occupied.add(World.grid.tileGrid[lowerLeft.x + i][lowerLeft.y + j]);
            }
        }
    }

    public Vec2 center() {
        int size = cdc.size.squares;
        return lowerLeft.LL().add(new Vec2(size / 2, size / 2));
    }

    public void moveToSquare(Vec2 pos) {
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
                occupied.add(World.grid.tileGrid[lowerLeft.x + i][lowerLeft.y + j]);
            }
        }
    }
}
