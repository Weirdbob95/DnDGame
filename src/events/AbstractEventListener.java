package events;

import creature.Creature;
import java.io.Serializable;

public abstract class AbstractEventListener implements EventListener, Serializable {

    private final Creature creature;

    public AbstractEventListener(Creature creature) {
        this.creature = creature;
        EventHandler.addListener(this);
        creature.elc.listenerList.add(this);
    }

    public void remove() {
        EventHandler.addListener(this);
        creature.elc.listenerList.remove(this);
    }
}
