package grid;

import core.AbstractEntity;

public class World extends AbstractEntity {

    public static GridComponent grid;

    public World() {
        //Components
        grid = add(new GridComponent());
        grid.load("lvl");
        //Systems
        add(new WorldRenderSystem(grid));
    }
}
