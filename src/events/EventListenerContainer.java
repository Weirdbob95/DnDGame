package events;

import creature.Creature;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class EventListenerContainer implements Serializable {

    private final Creature creature;
    private final HashMap<EventListener, Class<? extends Event>> map;
    private boolean enabled;

    public EventListenerContainer(Creature creature) {
        this.creature = creature;
        map = new HashMap();
        enabled = true;
    }

    public <E extends Event> EventListener<E> add(Class<E> e, EventListener<E> el) {
        map.put(el, e);
        EventHandler.addListener(el, e);
        creature.elc.listenerMap.put(el, e);
        return el;
    }

    public <E extends Event> EventListener<E> add(Class<E> e, double priority, EventListener<E> el) {
        return add(e, new EventListener<E>() {
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

    public void remove(EventListener el) {
        map.remove(el);
        EventHandler.removeListener(el, map.get(el));
        creature.elc.listenerMap.remove(el);
    }

    public void remove(List<EventListener> list) {
        list.forEach(this::remove);
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            if (enabled) {
                map.keySet().forEach(el -> {
                    EventHandler.addListener(el, map.get(el));
                    creature.elc.listenerMap.put(el, map.get(el));
                });
            } else {
                map.keySet().forEach(el -> {
                    EventHandler.removeListener(el, map.get(el));
                    creature.elc.listenerMap.remove(el);
                });
            }
        }
    }
}
