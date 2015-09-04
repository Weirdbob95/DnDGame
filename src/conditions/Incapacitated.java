package conditions;

import creature.Creature;
import events.HasActionTypeEvent;

public class Incapacitated extends Condition {

    public Incapacitated(Creature creature, Object source) {
        super(creature, source);
    }

    @Override
    public void init() {
        add(HasActionTypeEvent.class, e -> e.available = e.available && e.type == null);
    }
}
