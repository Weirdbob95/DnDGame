package grid;

import core.AbstractEntity;
import rounds.InitiativeOrderComponent;
import rounds.RoundSystem;

public class World extends AbstractEntity {

    public static GridComponent grid;

    public World() {
        //Components
        InitiativeOrderComponent ioc = add(new InitiativeOrderComponent());
        grid = add(new GridComponent());
        grid.load("lvl");
        //Systems
        add(new RoundSystem(ioc));
        add(new WorldRenderSystem(grid));
    }
}
