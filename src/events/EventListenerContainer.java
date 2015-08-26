package events;

import creature.Creature;
import java.io.Serializable;
import java.util.HashMap;

public class EventListenerContainer implements Serializable {

    private final Creature creature;
    private final HashMap<EventListener, Class<? extends Event>> map;
    private boolean enabled;

    public EventListenerContainer(Creature creature) {
        this.creature = creature;
        map = new HashMap();
        enabled = true;
    }

    public <E extends Event> void add(Class<E> e, EventListener<E> el) {
        map.put(el, e);
        EventHandler.addListener(el, e);
        creature.elc.listenerMap.put(el, e);
    }

    public <E extends Event> void add(Class<E> e, double priority, EventListener<E> el) {
        add(e, new EventListener<E>() {
            @Override
            public void onEvent(E e) {
                el.onEvent(e);
            }

            @Override
            public double priority() {
                return priority;
            }
        });
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            if (enabled) {
                for (EventListener el : map.keySet()) {
                    EventHandler.addListener(el, map.get(el));
                    creature.elc.listenerMap.put(el, map.get(el));
                }
            } else {
                for (EventListener el : map.keySet()) {
                    EventHandler.removeListener(el, map.get(el));
                    creature.elc.listenerMap.remove(el);
                }
            }
        }
    }
}
