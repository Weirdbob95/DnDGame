/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.barbarian;

import actions.Action;
import actions.Action.Type;
import static actions.Action.Type.BONUS_ACTION;
import actions.MoveAction;
import classes.Archetype;
import classes.barbarian.Barbarian.RageCheckEvent;
import creature.Creature;
import events.attack.AttackTargetEvent;
import items.Weapon;
import java.util.ArrayList;
import queries.BooleanQuery;
import queries.Query;
import queries.SelectQuery;
import util.Selectable;

/**
 *
 * @author RLund16
 */
public class Berserker extends Archetype<Barbarian> {

    public boolean isFrenzying;

    public Berserker(Barbarian playerClass) {
        super(playerClass);
    }

    @Override
    public void levelUp(int newLevel) {
        switch (newLevel) {
            case 3:
                add(RageCheckEvent.class, rce -> {
                    if (rce.rage.creature == playerClass.player) {
                        if (!rce.end) {
                            if (Query.ask(rce.rage.creature, new BooleanQuery("Do you want to use your Frenzy ability?")).response) {
                                rce.
                            }
                        }
                        if (rce.end) {
                            rce.rage.creature.exc.addExhaustion();

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

    public class BonusAttack extends Action {

        public BonusAttack(Creature creature) {
            super(creature);
        }

        @Override
        public Type getType() {
            return BONUS_ACTION;
        }

        @Override
        public String getName() {
            return "Bonus Attack";
        }

        @Override
        public boolean isAvailable() {
            return isFrenzying;
        }

        @Override
        protected void act() {
            singleAttack();
            ArrayList<Selectable> choices = new ArrayList();
            if (creature.amc.getAction(MoveAction.class).isAvailable()) {
                choices.add(creature.amc.getAction(MoveAction.class));

            }

        }

        public void singleAttack() {
            Weapon w = creature.wc.unarmedStrike;
            ArrayList<Weapon> weaponList = creature.wc.getAll(Weapon.class);
            if (!weaponList.isEmpty()) {
                weaponList.add(w);
                w = Query.ask(creature, new SelectQuery<Weapon>("Choose a weapon to attack with", weaponList)).response;
            }
            int range = Math.max(w.range, creature.cdc.reach.get() + (w.reach ? 5 : 0));
            new AttackTargetEvent(creature, w, range, this).call();
        }

        @Override
        public String[] defaultTabs() {
            return new String[]{"BonusAttack"};
        }

        @Override
        public String getDescription() {
            return "Use your Bonus Action to attack";
        }
    }

}
