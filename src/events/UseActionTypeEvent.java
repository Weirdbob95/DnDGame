package events;

import actions.Action.Type;
import creature.Creature;

public class UseActionTypeEvent extends Event {

    public Creature creature;
    public Type type;
    public Object purpose;

    public UseActionTypeEvent(Creature creature, Type type, Object purpose) {
        this.creature = creature;
        this.type = type;
        this.purpose = purpose;
    }
}
