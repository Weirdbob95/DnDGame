package events;

import conditions.Condition;
import creature.Creature;
import util.Log;

public class AddConditionEvent extends Event {

    public Creature creature;
    public Condition condition;
    public boolean add;

    public AddConditionEvent(Creature creature, Condition condition) {
        this.creature = creature;
        this.condition = condition;
        add = true;
    }

    @Override
    public void call() {
        super.call();
        if (add) {
            Log.print("Added condition " + condition);
            condition.init();
        }
    }
}
