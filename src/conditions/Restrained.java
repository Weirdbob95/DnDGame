package conditions;

import creature.Creature;
import static enums.AbilityScore.DEX;
import events.SavingThrowResultEvent;
import events.attack.AttackRollEvent;

public class Restrained extends Condition {

    public Restrained(Creature creature, Object source) {
        super(creature, source);
    }

    @Override
    public void init() {
        creature.spc.landSpeed.multComponents.put(this, 0.);
        add(AttackRollEvent.class, e -> {
            if (e.a.attacker == creature) {
                e.a.disadvantage = true;
            }
            if (e.a.target == creature) {
                e.a.advantage = true;
            }
        });
        add(SavingThrowResultEvent.class, e -> {
            if (e.ste.creature == creature) {
                if (e.ste.abilityScore == DEX) {
                    e.ste.disadvantage = true;
                }
            }
        });
    }

    @Override
    public void remove() {
        creature.spc.landSpeed.multComponents.remove(this);
        super.remove();
    }
}
