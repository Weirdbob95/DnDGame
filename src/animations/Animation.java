package animations;

import core.Main;
import grid.World;
import rounds.RoundSystem;

public abstract class Animation {

    public void start() {
        Main.gameManager.elc.getEntity(World.class).getSystem(RoundSystem.class).anim = this;
    }

    public abstract boolean update();
}
