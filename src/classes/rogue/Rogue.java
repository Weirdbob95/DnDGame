package classes.rogue;

import actions.Action;
import static actions.Action.Type.BONUS_ACTION;
import static actions.Action.Type.REACTION;
import actions.Dash;
import actions.Disengage;
import amounts.Value;
import classes.PlayerClass;
import conditions.Incapacitated;
import core.Core;
import creature.Creature;
import enums.AbilityScore;
import static enums.AbilityScore.*;
import enums.Skill;
import static enums.Skill.*;
import events.*;
import events.attack.AttackDamageRollEvent;
import events.attack.AttackEvent;
import events.attack.AttackResultEvent;
import events.attack.AttackRollEvent;
import grid.GridUtils;
import java.util.ArrayList;
import player.ExpertiseComponent;
import player.Player;
import queries.BooleanQuery;
import queries.Query;
import queries.SelectQuery;
import util.Mutable;

public class Rogue extends PlayerClass {

    public Rogue(Player player) {
        super(player);
    }

    @Override
    public int archetypeLevel() {
        return 3;
    }

    @Override
    public int hitDie() {
        return 8;
    }

    @Override
    public void levelUp(int newLevel) {
        switch (newLevel) {
            case 1:
                ExpertiseComponent ec = player.getOrAdd(new ExpertiseComponent(player));
                ec.chooseExpertise();
                ec.chooseExpertise();

                Mutable<Boolean> hasSneakAttack = new Mutable(true);
                add(TurnStartEvent.class, e -> hasSneakAttack.o = true);
                add(AttackDamageRollEvent.class, e -> {
                    if (hasSneakAttack.o) {
                        if (e.a.isWeapon) {
                            if (!e.a.disadvantage) {
                                if (e.a.weapon.finesse && e.a.weapon.isRanged) {
                                    if (e.a.advantage || Core.gameManager.elc.getEntityList(Creature.class).stream().anyMatch(c
                                            -> e.a.target != c && GridUtils.minDistance(e.a.target, c) <= 5 && !c.cnc.conditionMap.containsKey(Incapacitated.class)
                                    )) {
                                        if (Query.ask(player, new BooleanQuery("Make a Sneak Attack?")).response) {
                                            e.a.damage.set("Sneak Attack", Value.parseValue((level + 1) / 2 + "d6"));
                                            hasSneakAttack.o = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                });

                player.lc.languages.add("Thieves' Cant");
                break;
            case 2:
                player.amc.addAction(new Cunning_Action(player));
                break;
            case 5:
                add(TakeDamageEvent.class, e -> {
                    if (e.target == player) {
                        if (e.source instanceof AttackEvent) {
                            if (player.amc.hasType(REACTION)) {
                                if (Query.ask(player, new BooleanQuery("Use Uncanny Dodge?")).response) {
                                    e.damage /= 2;
                                }
                            }
                        }
                    }
                });
                break;
            case 6:
                ec = player.getComponent(ExpertiseComponent.class);
                ec.chooseExpertise();
                ec.chooseExpertise();
            case 7:
                //Evasion
                break;
            case 10:
                abilityScoreImprovement();
                break;
            case 11:
                add(AbilityCheckResultEvent.class, e -> {
                    if (e.ace.bonus.components.containsKey("Proficiency")) {
                        if (e.ace.roll < 10) {
                            e.ace.roll = 10;
                        }
                    }
                });
                break;
            case 14:
                //Blindsense
                break;
            case 15:
                player.pc.savingThrowProfs.add(WIS);
                break;
            case 18:
                add(AttackRollEvent.class, 2, e -> {
                    if (e.a.target == player) {
                        if (e.a.advantage) {
                            if (!player.cnc.conditionMap.containsKey(Incapacitated.class)) {
                                e.a.advantage = false;
                            }
                        }
                    }
                });
                break;
            case 20:
                Mutable<Boolean> available = new Mutable(true);
                add(ShortRestEvent.class, e -> available.o = true);
                add(LongRestEvent.class, e -> available.o = true);
                add(AttackResultEvent.class, e -> {
                    if (e.a.attacker == player) {
                        if (available.o) {
                            if (!e.a.hit()) {
                                if (Query.ask(player, new BooleanQuery("Use Stroke of Luck?")).response) {
                                    e.a.roll = 9999;
                                    available.o = false;
                                }
                            }
                        }
                    }
                });
                add(AbilityCheckResultEvent.class, e -> {
                    if (e.ace.creature == player) {
                        if (available.o) {
                            if (!e.ace.success()) {
                                if (Query.ask(player, new BooleanQuery("Use Stroke of Luck?")).response) {
                                    e.ace.roll = 20;
                                    available.o = false;
                                }
                            }
                        }
                    }
                });
                break;
        }
    }

    @Override
    public AbilityScore[] savingThrows() {
        return new AbilityScore[]{DEX, INT};
    }

    @Override
    public int skillAmount() {
        return 4;
    }

    @Override
    public Skill[] skills() {
        return new Skill[]{Acrobatics, Athletics, Deception, Insight, Investigation, Perception, Performance, Persuasion, Sleight_of_Hand, Stealth};
    }

    public class Cunning_Action extends Action {

        public ArrayList<Action> choices;

        public Cunning_Action(Creature creature) {
            super(creature);
            choices = new ArrayList();
            choices.add(player.amc.getAction(Dash.class));
            choices.add(player.amc.getAction(Disengage.class));
        }

        @Override
        protected void act() {
            Action choice = Query.ask(player, new SelectQuery<Action>("Choose which action to use", choices)).response;
            choice.useNoType();
        }

        @Override
        public String[] defaultTabs() {
            return new String[]{};
        }

        @Override
        public String getDescription() {
            return "Your quick thinking and agility allow you to move and act quickly. You take the Dash, Disengage, or Hide action as a bonus action.";
        }

        @Override
        public String getName() {
            return "Cunning Action";
        }

        @Override
        public Type getType() {
            return BONUS_ACTION;
        }
    }
}
