package grid;

import core.AbstractEntity;
import rounds.InitiativeOrderComponent;
import rounds.RoundSystem;

public class World extends AbstractEntity {

    public World() {
        //Components
        InitiativeOrderComponent ioc = add(new InitiativeOrderComponent());
        GridComponent gc = add(new GridComponent("lvl"));
        //Systems
        add(new RoundSystem(ioc));
        add(new WorldRenderSystem(gc));
    }
}
