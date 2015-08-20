package events;

import creature.Creature;
import java.io.Serializable;

public abstract class AbstractEventListener implements EventListener, Serializable {

    public AbstractEventListener() {
        EventHandler.addListener(this);
    }

    public AbstractEventListener(Creature c) {
        EventHandler.addListener(this);
        c.elc.listenerList.add(this);
    }
}
