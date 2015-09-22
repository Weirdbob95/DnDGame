/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conditions;

import creature.Creature;
import events.AbilityCheckEvent;

/**
 *
 * @author RLund16
 */
public class Exhaustion extends Condition {

    public int ExhaustionLevel;

    public Exhaustion(Creature creature, Object source, int ExhaustionLevel) {
        super(creature, source);
        this.ExhaustionLevel = ExhaustionLevel;
    }

    @Override
    public void init() {
        switch (ExhaustionLevel) {
            case 2:
                creature.spc.landSpeed.multComponents.put("ExhaustionSpeed", .5);

            case 1:
                add(AbilityCheckEvent.class, e -> {
                    if (e.creature == creature) {
                        e.advantage = false;
                    }
                });
        }
    }

    public void setExhaustionLevel(int plus) {
        this.ExhaustionLevel = plus;
    }

}
