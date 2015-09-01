package conditions;

import creature.Creature;
import events.AddConditionEvent;
import events.EventListenerContainer;

public abstract class Condition extends EventListenerContainer {

    public Creature creature;
    public Object source;

    public Condition(Creature creature, Object source) {
        super(creature);
        this.creature = creature;
        creature.cnc.getConditions(getClass()).put(source, this);
    }

    public boolean add() {
        return new AddConditionEvent(creature, this).add;
    }

    public abstract void init();

    public void remove() {
        setEnabled(false);
    }

    public void removeSelf() {
        creature.cnc.conditionMap.get(getClass()).remove(source);
        remove();
    }
}
