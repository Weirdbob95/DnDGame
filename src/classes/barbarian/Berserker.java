/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.barbarian;

import static actions.Action.Type.BONUS_ACTION;
import actions.AttackAction;
import classes.Archetype;
import classes.barbarian.Barbarian.RageCheckEvent;
import creature.Creature;
import queries.BooleanQuery;
import queries.Query;

/**
 *
 * @author RLund16
 */
public class Berserker extends Archetype<Barbarian> {

    public Berserker(Barbarian playerClass) {
        super(playerClass);
    }

    @Override
    public void levelUp(int newLevel) {
        switch (newLevel) {
            case 3:
                add(RageCheckEvent.class, rce -> {
                    if (rce.rage.creature == playerClass.player) {
                        if (rce.rage.turnsElapsed == 0) {
                            if (Query.ask(rce.rage.creature, new BooleanQuery("Do you want to use your Frenzy ability?")).response) {

                            }
                        }
                    }
                });
                break;
            case 6:
                break;
            case 10:

                break;
            case 14:
                //Retaliation
                break;
        }
    }

    public class BonusAttack extends AttackAction {

        public BonusAttack(Creature creature) {
            super(creature);
        }

        @Override
        public Type getType() {
            return BONUS_ACTION;
        }

    }

}
