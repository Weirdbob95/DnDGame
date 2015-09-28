/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creature;

import amounts.ConditionalDoubleAmount;
import core.AbstractComponent;
import events.*;

/**
 *
 * @author RLund16
 */
public class ExhaustionComponent extends AbstractComponent {

    public Creature creature;
    public int exhaustionLevel;

    public ExhaustionComponent(Creature creature) {
        this.creature = creature;
        EventListener.createListener(creature, AbilityCheckEvent.class, e -> {
            if (e.creature == creature) {
                if (e.creature.exc.exhaustionLevel >= 1) {
                    e.advantage = false;
                }
            }

        });

        creature.spc.landSpeed.multComponents.put("ExhaustionTwo", new ConditionalDoubleAmount(() -> exhaustionLevel >= 2, () -> .5, () -> 1.));

        EventListener.createListener(creature, SavingThrowEvent.class, e -> {
            if (e.creature == creature) {
                if (e.creature.exc.exhaustionLevel >= 3) {
                    e.disadvantage = true;
                }
            }
        });
        /**
         * EventListener.createListener(creature, AttackRollEvent.class, e->{
         * if(e.creature==creature) if(e.creature.exc.exhaustionLevel>=3)
         * e.disadvantage = true; });
         *
         */

        creature.spc.landSpeed.multComponents.put("ExhaustionFive", new ConditionalDoubleAmount(() -> exhaustionLevel >= 2, () -> 0., () -> 1.));

        //Change both 4  and 5 top conditional amounts
        /**
         * 6 levels of exhaustion Exhaustion levels stack Have functions that
         * add and subtract exhaustion (for exhaustion and rest) Each creature
         * has its own level of exhaustion Not necessary to know the source of
         * the exhaustion Exhaustion will be a component not a condition * 4th
         * level of exhaustion should be a conditional amount. It will be added
         * onto health in all situations.
         *
         *
         */
    }

    public void addExhaustion() {
        this.exhaustionLevel += 1;
        //if exhaustion =5 and you add to it, creature dies
    }

    public void subtractExhaustion() {
        this.exhaustionLevel -= 1;
    }

}
