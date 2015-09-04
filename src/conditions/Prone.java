package conditions;

import creature.Creature;
import events.attack.AttackRollEvent;
import grid.GridUtils;

public class Prone extends Condition {

    public Prone(Creature creature) {
        super(creature, "Prone");
    }

    @Override
    public void init() {
        add(AttackRollEvent.class, e -> {
            if (e.a.attacker == creature) {
                e.a.disadvantage = true;
            }
            if (e.a.target == creature) {
                if (GridUtils.minDistance(e.a.attacker, creature) <= 5) {
                    e.a.advantage = true;
                } else {
                    e.a.disadvantage = true;
                }
            }
        });
        creature.spc.landSpeed.multComponents.put(this, .5);
    }

    @Override
    public void remove() {
        super.remove();
        creature.spc.landSpeed.multComponents.remove(this);
    }
}
