package events;

import creature.Creature;
import java.io.Serializable;

@FunctionalInterface
public interface EventListener<E extends Event> extends Serializable {

    public default void addToCreature(Creature c, Class<E> e) {
        EventHandler.addListener(this, e);
        c.elc.listenerMap.put(this, e);
    }

    public static <E extends Event> void createListener(Creature c, Class<E> e, EventListener<E> el) {
        el.addToCreature(c, e);
    }

    public static <E extends Event> void createListener(Creature c, Class<E> e, double priority, EventListener<E> el) {
        EventListener<E> list = new EventListener<E>() {
            @Override
            public void onEvent(E e) {
                el.onEvent(e);
            }

            @Override
            public double priority() {
                return priority;
            }
        };
        list.addToCreature(c, e);
    }

    public void onEvent(E e);

    public default double priority() {
        return 0;
    }
}
