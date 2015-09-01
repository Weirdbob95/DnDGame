package grid;

import core.AbstractEntity;

public class World extends AbstractEntity {

    public World() {
        //Components
        GridComponent grid = add(new GridComponent());
        grid.load("lvl");
        //Systems
        add(new WorldRenderSystem(grid));
    }
}
