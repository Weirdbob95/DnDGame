/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.barbarian;

import actions.Action;
import actions.Action.Type;
import static actions.Action.Type.*;
import actions.AttackAction;
import classes.Archetype;
import classes.barbarian.Barbarian.Rage;
import classes.barbarian.Barbarian.RageCheckEvent;
import conditions.*;
import creature.Creature;
import enums.AbilityScore;
import events.*;
import events.attack.AttackTargetEvent;
import grid.GridUtils;
import grid.Square;
import items.Weapon;
import java.util.ArrayList;
import queries.*;
import util.Mutable;

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
                player().amc.addAction(new IntimidatingPresence(player()));
                break;
            case 14:
                //Retaliation
                add(TakeDamageEvent.class, tde -> {
                    if (tde.target == player()) {
                        if (GridUtils.minDistance(player(), tde.attacker) < 5) {
                            if (Query.ask(player(), new BooleanQuery("Would you like to use your Retalitation ability")).response) {
                                player().amc.getAction(AttackAction.class);
                            }
                        }
                    }
                });
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

    public class IntimidatingPresence extends Action {

        public boolean use;

        public IntimidatingPresence(Creature creature) {
            super(creature);
        }

        @Override
        protected void act() {
            intimidate();
        }

        @Override
        public String getName() {
            return "Intimidating Presence";
        }

        @Override
        public String[] defaultTabs() {
            return new String[]{"IntimidatingPresence"};
        }

        @Override
        public Type getType() {
            return ACTION;
        }

        @Override
        public String getDescription() {
            return "Frighten a Character within that can see and hear you within 30 feet";
        }

        public void intimidate() {
            boolean already = false;
            int range = 30;
            Square toIntimidate = Query.ask(creature, new SquareQuery("Choose a creature to Intimidate", creature.glc.occupied, range, true)).response;
            Creature enemy = toIntimidate.creature;
            if (toIntimidate != null && toIntimidate.creature != null) {
                if (enemy.cnc.hasAny(Frightened.class)) {
                    already = true;
                }
                if (SavingThrowEvent.fail(enemy, AbilityScore.WIS, 8 + player().pc.prof.get() + player().asc.mod(AbilityScore.CHA).get()));
                Frightened f = new Frightened(enemy, player());
                if (f.add() || already) {
                    Mutable<Boolean> isNextTurn = new Mutable(false);
                    f.add(TurnStartEvent.class, ev -> isNextTurn.o = (ev.creature == player() ? true : isNextTurn.o));
                    f.add(TurnEndEvent.class, ev -> {
                        if (ev.creature == player() && isNextTurn.o) {
                            f.remove();
                        } else if (GridUtils.minDistance(player(), enemy) >= 60) {
                            f.remove();
                        }

                    });
                    //else
                    //disable IntimatingPresence for 24 hours
                }
            }
        }
    }
}
