package conditions;

import creature.Creature;

public class Grappled extends Condition {

    public Creature other;

    public Grappled(Creature creature, Creature other) {
        super(creature, other);
        this.other = other;
    }

    @Override
    public void init() {
        creature.spc.landSpeed.multComponents.put(this, 0.);
    }

    @Override
    public void remove() {
        creature.spc.landSpeed.multComponents.remove(this);
        super.remove();
    }
}
