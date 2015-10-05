/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.barbarian;

import actions.Action;
import actions.Action.Type;
import static actions.Action.Type.BONUS_ACTION;
import classes.Archetype;
import classes.barbarian.Barbarian.RageCheckEvent;
import conditions.Charmed;
import conditions.Condition;
import conditions.Frightened;
import creature.Creature;
import events.AddConditionEvent;
import events.attack.AttackTargetEvent;
import items.Weapon;
import java.util.ArrayList;
import queries.BooleanQuery;
import queries.Query;
import queries.SelectQuery;

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
                    if (rce.rage.creature == player()) {
                        if (!rce.end) {
                            if (Query.ask(rce.rage.creature, new BooleanQuery("Do you want to use your Frenzy ability?")).response) {
                                isFrenzying = true;
                            }
                        }
                        if (rce.end) {
                            if (isFrenzying) {
                                rce.rage.creature.exc.addExhaustion();
                                isFrenzying = false;
                            }
                        }
                    }
                });
                player().amc.addAction(new BonusAttack(player())); //player() == playerClass.player
                break;
            case 6:
                ArrayList<Condition> temp = new ArrayList<>();
                add(RageCheckEvent.class, rce -> {
                    if (rce.rage.creature == player()) {
                        if (!rce.end) {
                           //set add condition to false
                            //make a list that removes conditions and puts them
                            //in a different list and then adds them back in at the end of the rage
                            add(AddConditionEvent.class, ace -> {
                                if (ace.condition == Charmed || ace.condition == Frightened) {
                                    ace.add = false;
                                }
                            });
                            if (rce.rage.creature.cnc.hasAny(Frightened.class)) {
                                temp.addAll(player().cnc.getConditions(Frightened.class).values());
                                player().cnc.conditionMap.remove(Frightened.class);
                            }
                            if (rce.rage.creature.cnc.hasAny(Charmed.class)) {
                                temp.addAll(player().cnc.getConditions(Charmed.class).values());
                                player().cnc.remove(Charmed.class);
                            }
                        }
                    }
                });
                break;
            case 10:
                //Intmidating Presence
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
        }

        public void singleAttack() {
            Weapon w = creature.wc.unarmedStrike;
            ArrayList<Weapon> weaponList = creature.wc.getAll(Weapon.class);
            weaponList.removeIf(w2 -> w2.isRanged);
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
