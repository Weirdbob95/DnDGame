package conditions;

import creature.Creature;
import events.AddConditionEvent;
import events.EventListenerContainer;
import java.util.ArrayList;

public abstract class Condition extends EventListenerContainer {

    public Creature creature;
    public Object source;
    private ArrayList<Condition> subConditions;

    public Condition(Creature creature, Object source) {
        super(creature);
        this.creature = creature;
        subConditions = new ArrayList();
        creature.cnc.getConditions(getClass()).put(source, this);
    }

    public boolean add() {
        return new AddConditionEvent(creature, this).add;
    }
    
    public void addSubCondition(Condition c) {
        c.add();
        subConditions.add(c);
    }

    public abstract void init();

    public void remove() {
        setEnabled(false);
        subConditions.forEach(Condition::remove);
    }

    public void removeSelf() {
        creature.cnc.conditionMap.get(getClass()).remove(source);
        remove();
    }
}
