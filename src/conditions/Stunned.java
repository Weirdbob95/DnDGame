package conditions;

import creature.Creature;
import static enums.AbilityScore.DEX;
import static enums.AbilityScore.STR;
import events.SavingThrowResultEvent;
import events.attack.AttackRollEvent;

public class Stunned extends Condition {

    public Stunned(Creature creature, Object source) {
        super(creature, source);
    }

    @Override
    public void init() {
        addSubCondition(new Incapacitated(creature, source));
        creature.spc.landSpeed.multComponents.put(this, () -> 0.);
        add(AttackRollEvent.class, e -> {
            if (creature == e.a.target) {
                e.a.advantage = true;
            }
        });
        add(SavingThrowResultEvent.class, e -> {
            if (e.ste.creature == creature) {
                if (e.ste.abilityScore == STR || e.ste.abilityScore == DEX) {
                    e.ste.roll = -9999;
                }
            }
        });
    }

    @Override
    public void remove() {
        super.remove();
        creature.spc.landSpeed.multComponents.remove(this);
    }
}
