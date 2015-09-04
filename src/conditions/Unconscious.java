package conditions;

import creature.Creature;

public class Unconscious extends Condition {

    public Unconscious(Creature creature, Object source) {
        super(creature, source);
    }

    @Override
    public void init() {
        addSubCondition(new Paralyzed(creature, source));
        new Prone(creature).add();
        for (int i = 0; i < creature.wc.hands; i++) {
            creature.wc.held[i] = null;
        }
    }
}
