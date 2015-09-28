package maneuvers;

import events.EventListenerContainer;
import player.Player;
import util.Selectable;
import util.Util;

public abstract class Maneuver extends EventListenerContainer implements Selectable {

    public Player player;
    public ManeuversComponent mc;
    public String description;

    public Maneuver(Player player, ManeuversComponent mc) {
        super(player);
        this.player = player;
        this.mc = mc;
    }

    public void forget() {
        setEnabled(false);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return Util.classToName(this);
    }

    public void learn() {
        setEnabled(true);
    }
}
