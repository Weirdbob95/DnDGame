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
import classes.barbarian.Barbarian.Rage;
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
                        if (rce.start) {
                            if (Query.ask(player(), new BooleanQuery("Do you want to use your Frenzy ability?")).response) {
                                //this query is not being asked; why?
                                isFrenzying = true;
                            }
                        } else {
                            if (isFrenzying) {
                                player().exc.addExhaustion();
                                isFrenzying = false;
                            }
                        }
                    }
                });
                player().amc.addAction(new BonusAttack(player())); //player() == playerClass.player
                break;
            case 6:
                ArrayList<Condition> temp = new ArrayList<>();
                add(AddConditionEvent.class, ace -> {
                    if (ace.condition instanceof Charmed || ace.condition instanceof Frightened) {
                        if (player().amc.getAction(Rage.class).raging) {
                            ace.add = false;
                        }
                    }
                });

                //make a list that removes conditions and puts them
                //in a different list and then adds them back in at the end of the rage
                add(RageCheckEvent.class, rce -> {
                    if (rce.rage.creature == player()) {
                        if (rce.start) {
                            //set add condition to false

                            if (player().cnc.hasAny(Frightened.class)) {
                                temp.addAll(player().cnc.getConditions(Frightened.class).values());
                                player().cnc.conditionMap.remove(Frightened.class);
                            }
                            if (player().cnc.hasAny(Charmed.class)) {
                                temp.addAll(player().cnc.getConditions(Charmed.class).values());
                                player().cnc.remove(Charmed.class);
                            }
                        } else {
                            temp.forEach(c -> c.init());
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
