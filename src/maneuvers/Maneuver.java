package maneuvers;

import events.AbstractEventListener;
import player.Player;
import util.Selectable;

public abstract class Maneuver extends AbstractEventListener implements Selectable {

    public Player player;
    public ManeuversComponent mc;
    public String description;

    public Maneuver(Player player, ManeuversComponent mc) {
        super(player);
        this.player = player;
        this.mc = mc;
    }

    public void forget() {
        remove();
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName().replace("__", "'").replace("_", " ");
    }

    public void learn() {
    }
}
