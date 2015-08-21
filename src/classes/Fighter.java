package classes;

import actions.Action;
import static actions.Action.Type.ACTION;
import static actions.Action.Type.BONUS_ACTION;
import actions.AttackAction;
import amounts.Die;
import amounts.MultipliedAmount;
import creature.Creature;
import enums.AbilityScore;
import static enums.AbilityScore.CON;
import static enums.AbilityScore.STR;
import enums.FightingStyle;
import enums.Skill;
import static enums.Skill.*;
import events.*;
import events.attack.AttackResultEvent;
import player.Player;
import queries.BooleanQuery;
import queries.Query;
import queries.SelectQuery;
import util.Selectable;

public class Fighter extends PlayerClass {

    public enum Archetype implements Selectable {

        Champion("The archetypal Champion focuses on the development of raw physical power honed to deadly perfection."),
        Battle_Master("Those who emulate the archetypal Battle Master employ martial techniques passed down through generations."),
        Eldritch_Knight("The archetypal Eldritch Knight combines the martial mastery common to all fighters with a careful stufy of magic.");

        public final String description;

        private Archetype(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public String getName() {
            return name().replaceAll("_", " ");
        }
    }

    public Archetype archetype;

    public Fighter(Player player) {
        super(player);
    }

    @Override
    public int hitDie() {
        return 10;
    }

    @Override
    public void levelUp(int newLevel) {
        switch (newLevel) {
            case 1:
                player.fsc.chooseFightingStyle(FightingStyle.values());
                player.amc.actions.add(new Second_Wind(player));
                break;
            case 2:
                player.amc.actions.add(new Action_Surge(player));
                break;
            case 3:
                archetype = Query.ask(player, new SelectQuery<Archetype>("Choose your character's archetype", Archetype.values())).response;
                switch (archetype) {
                    case Champion:
                        new AbstractEventListener(player) {
                            @Override
                            public Class<? extends Event>[] callOn() {
                                return new Class[]{AttackResultEvent.class};
                            }

                            @Override
                            public void onEvent(Event e) {
                                AttackResultEvent are = (AttackResultEvent) e;
                                if (are.a.isWeapon && !are.a.isCritical) {
                                    if (are.a.roll == 19 || (are.a.roll == 18 && level >= 15)) {
                                        are.a.isCritical = true;
                                    }
                                }
                            }
                        };
                        break;
                }
                break;
            case 4:
                abilityScoreImprovement();
                break;
            case 5:
                player.amc.getAction(AttackAction.class).setExtraAttacks(1);
                break;
            case 6:
                abilityScoreImprovement();
                break;
            case 7:
                switch (archetype) {
                    case Champion:
                        new AbstractEventListener(player) {
                            @Override
                            public Class<? extends Event>[] callOn() {
                                return new Class[]{AbilityCheckEvent.class};
                            }

                            @Override
                            public void onEvent(Event e) {
                                AbilityCheckEvent ace = (AbilityCheckEvent) e;
                                switch (ace.abilityScore) {
                                    case STR:
                                    case DEX:
                                    case CON:
                                        if (!ace.bonus.components.containsKey("Proficiency")) {
                                            ace.bonus.set("Proficiency", new MultipliedAmount(player.pc.prof, .5));
                                        }
                                }
                            }
                        };
                        break;
                }
                break;
            case 8:
                abilityScoreImprovement();
                break;
            case 9:
                new AbstractEventListener(player) {
                    public int timesUsed = 0;

                    @Override
                    public Class<? extends Event>[] callOn() {
                        return new Class[]{SavingThrowResultEvent.class, LongRestEvent.class};
                    }

                    @Override
                    public void onEvent(Event e) {
                        if (e instanceof LongRestEvent) {
                            timesUsed = 0;
                        } else {
                            int cap = 1;
                            if (level >= 13) {
                                cap++;
                                if (level >= 17) {
                                    cap++;
                                }
                            }
                            if (timesUsed < cap) {
                                SavingThrowResultEvent stre = (SavingThrowResultEvent) e;
                                if (!stre.ste.success()) {
                                    if (Query.ask(player, new BooleanQuery("Use the Indomitable ability?")).response) {
                                        stre.ste.roll = new Die(20).roll;
                                        timesUsed++;
                                    }
                                }
                            }
                        }
                    }
                };
                break;
            case 10:
                switch (archetype) {
                    case Champion:
                        player.fsc.chooseFightingStyle(FightingStyle.values());
                        break;
                }
                break;
            case 11:
                player.amc.getAction(AttackAction.class).setExtraAttacks(2);
                break;
            case 12:
                abilityScoreImprovement();
                break;
            case 14:
                abilityScoreImprovement();
                break;
            case 15:
                switch (archetype) {
                }
                break;
            case 16:
                abilityScoreImprovement();
                break;
            case 18:
                switch (archetype) {
                    case Champion:
                        new AbstractEventListener(player) {
                            @Override
                            public Class<? extends Event>[] callOn() {
                                return new Class[]{TurnStartEvent.class};
                            }

                            @Override
                            public void onEvent(Event e) {
                                if (player.hc.currentHealth.get() > 0 && player.hc.currentHealth.get() <= player.hc.maxHealth.get() / 2) {
                                    player.hc.heal(5 + player.asc.mod(CON).get());
                                }
                            }
                        };
                        break;
                }
                break;
            case 19:
                abilityScoreImprovement();
                break;
            case 20:
                player.amc.getAction(AttackAction.class).setExtraAttacks(3);
                break;
        }
    }

    @Override
    public AbilityScore[] savingThrows() {
        return new AbilityScore[]{STR, CON};
    }

    @Override
    public Skill[] skills() {
        return new Skill[]{Acrobatics, Animal_Handling, Athletics, History, Insight, Intimidation, Perception, Survival};
    }

    public class Second_Wind extends Action implements EventListener {

        public boolean available = true;

        public Second_Wind(Creature creature) {
            super(creature);
        }

        @Override
        protected void act() {
            creature.hc.heal(new Die(10).roll + level);
            available = false;
        }

        @Override
        public Class<? extends Event>[] callOn() {
            return new Class[]{ShortRestEvent.class, LongRestEvent.class};
        }

        @Override
        public String[] defaultTabs() {
            return new String[]{};
        }

        @Override
        public String getDescription() {
            return "You have a limited well of stamina that you can draw on to protect yourself from harm. You regain 1d10 + " + level + " hit points.";
        }

        @Override
        public Type getType() {
            return BONUS_ACTION;
        }

        @Override
        public boolean isAvailable() {
            return available;
        }

        @Override
        public void onEvent(Event e) {
            available = true;
        }
    }

    public class Action_Surge extends Action implements EventListener {

        public boolean available = true;
        public int uses = 1;

        public Action_Surge(Creature creature) {
            super(creature);
        }

        @Override
        protected void act() {
            creature.amc.available.add(ACTION);
            available = false;
            uses--;
        }

        @Override
        public Class<? extends Event>[] callOn() {
            return new Class[]{TurnStartEvent.class, ShortRestEvent.class, LongRestEvent.class};
        }

        @Override
        public String[] defaultTabs() {
            return new String[]{};
        }

        @Override
        public String getDescription() {
            return "You can push yourself beyond your normal limits for a moment. This turn, you can take on additional action on top of your regular action.";
        }

        @Override
        public String getName() {
            return "Action Surge";
        }

        @Override
        public Action.Type getType() {
            return null;
        }

        @Override
        public boolean isAvailable() {
            return available && uses > 0;
        }

        @Override
        public void onEvent(Event e) {
            if (e instanceof TurnStartEvent) {
                available = true;
            } else {
                if (level < 17) {
                    uses = 1;
                } else {
                    uses = 2;
                }
            }
        }
    }
}
