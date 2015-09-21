package conditions;

import creature.Creature;

public class Charmed extends Condition {

    public Creature other;

    public Charmed(Creature creature, Creature other) {
        super(creature, other);
        this.other = other;
    }

    @Override
    public void init() {
    }
}
