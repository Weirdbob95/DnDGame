package events;

import creature.Creature;

public interface EventListener {

    public default void addToCreature(Creature c) {
        EventHandler.addListener(this);
        c.elc.listenerList.add(this);
    }

    public Class<? extends Event>[] callOn();

    public void onEvent(Event e);

    public default double priority() {
        return 0;
    }
}
