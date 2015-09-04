package conditions;

import creature.Creature;
import events.AbilityCheckEvent;
import events.attack.AttackRollEvent;

public class Poisoned extends Condition {

    public Poisoned(Creature creature, Object source) {
        super(creature, source);
    }

    @Override
    public void init() {
        add(AbilityCheckEvent.class, e -> {
            if (e.creature == creature) {
                e.disadvantage = true;
            }
        });
        add(AttackRollEvent.class, e -> {
            if (e.a.attacker == creature) {
                e.a.disadvantage = true;
            }
        });
    }
}
