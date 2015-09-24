/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creature;

import core.AbstractComponent;
import events.AbilityCheckEvent;
import events.EventListener;
import events.TurnStartEvent;

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
                if (e.creature.) {
                    e.advantage = false;
                }
            }
        });

        EventListener.createListener(creature, TurnStartEvent.class, e -> {

        });
    }

    public void addExhaustion() {
        this.exhaustionLevel += 1;
    }

    public void subtractExhaustion() {
        this.exhaustionLevel -= 1;
    }

    public void exhausted() {
        switch (exhaustionLevel) {
            case 2:
                creature.spc.landSpeed.multComponents.put("ExhaustionSpeed", .5);

            case 1:
                add(AbilityCheckEvent.class, e -> {

                });
        }
    }
}
