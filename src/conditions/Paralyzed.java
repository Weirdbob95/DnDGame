package conditions;

import creature.Creature;
import events.attack.AttackDamageRollEvent;
import grid.GridUtils;

public class Paralyzed extends Condition {

    public Paralyzed(Creature creature, Object source) {
        super(creature, source);
    }

    @Override
    public void init() {
        addSubCondition(new Stunned(creature, source));
        add(AttackDamageRollEvent.class, e -> {
            if (e.a.target == creature) {
                if (GridUtils.minDistance(e.a.attacker, creature) <= 5) {
                    e.a.isCritical = true;
                }
            }
        });
    }
}
