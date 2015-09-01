package conditions;

import creature.Creature;
import events.attack.AttackRollEvent;

public class Blinded extends Condition {

    public Blinded(Creature creature, Object source) {
        super(creature, source);
    }

    @Override
    public void init() {
        add(AttackRollEvent.class, e -> {
            if (e.a.attacker == creature) {
                e.a.disadvantage = true;
            }
            if (e.a.target == creature) {
                e.a.advantage = true;
            }
        });
    }
}
