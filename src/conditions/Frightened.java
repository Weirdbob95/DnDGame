package conditions;

import creature.Creature;
import events.AbilityCheckEvent;
import events.attack.AttackRollEvent;
import events.move.SegmentCheckEvent;
import grid.GridUtils;

public class Frightened extends Condition {

    public Creature other;

    public Frightened(Creature creature, Creature other) {
        super(creature, other);
        this.other = other;
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
        add(SegmentCheckEvent.class, e -> {
            if (e.creature == creature) {
                int oldDist = GridUtils.minDistance(creature.glc.occupiedAt(e.from), other.glc.occupied);
                int newDist = GridUtils.minDistance(creature.glc.occupiedAt(e.to), other.glc.occupied);
                if (newDist < oldDist) {
                    e.allow = false;
                }
            }
        });
    }
}
