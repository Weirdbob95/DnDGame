package events;

import actions.Action.Type;
import creature.Creature;

public class HasActionTypeEvent extends Event {

    public Creature creature;
    public Type type;
    public boolean available;

    public HasActionTypeEvent(Creature creature, Type type, boolean available) {
        this.creature = creature;
        this.type = type;
        this.available = available;
    }
}
