package player;

import amounts.Stat;
import core.AbstractComponent;
import creature.Creature;
import events.EventListener;
import events.ShortRestEvent;

public class KiComponent extends AbstractComponent {

    public Stat maximumKi = new Stat();
    public Stat currentKi = new Stat("Maximum", maximumKi);

    public KiComponent(Creature creature) {
        EventListener.createListener(creature, ShortRestEvent.class, e -> currentKi.components.remove("Used"));
    }

    public int getKi() {
        return currentKi.get();
    }

    public int getMaxKi() {
        return maximumKi.get();
    }

    public void useKi(int kiUsed) {
        currentKi.edit("Used", -kiUsed);
    }
}
