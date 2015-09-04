package conditions;

import creature.Creature;
import events.attack.AttackRollEvent;

public class Invisible extends Condition {

    public Invisible(Creature creature, Object source) {
        super(creature, source);
    }

    @Override
    public void init() {
        add(AttackRollEvent.class, e -> {
            if (e.a.attacker == creature) {
                e.a.advantage = true;
            }
            if (e.a.target == creature) {
                e.a.disadvantage = true;
            }
        });
    }
}
