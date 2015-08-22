package classes.fighter;

import actions.Action;
import static actions.Action.Type.ACTION;
import static actions.Action.Type.BONUS_ACTION;
import actions.AttackAction;
import amounts.Die;
import classes.PlayerClass;
import creature.Creature;
import enums.AbilityScore;
import static enums.AbilityScore.CON;
import static enums.AbilityScore.STR;
import enums.FightingStyle;
import enums.Skill;
import static enums.Skill.*;
import events.*;
import player.Player;
import queries.BooleanQuery;
import queries.Query;

public class Fighter extends PlayerClass {

    @Override
    public int archetypeLevel() {
        return 3;
    }

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
            case 4:
            case 6:
            case 8:
            case 12:
            case 14:
            case 16:
            case 19:
                abilityScoreImprovement();
                break;
            case 5:
                player.amc.getAction(AttackAction.class).setExtraAttacks(1);
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
                            SavingThrowResultEvent stre = (SavingThrowResultEvent) e;
                            if (stre.ste.creature == player) {
                                int cap = 1;
                                if (level >= 13) {
                                    cap++;
                                    if (level >= 17) {
                                        cap++;
                                    }
                                }
                                if (timesUsed < cap) {
                                    if (!stre.ste.success()) {
                                        if (Query.ask(player, new BooleanQuery("Use the Indomitable ability?")).response) {
                                            stre.ste.roll = new Die(20).roll;
                                            timesUsed++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                };
                break;
            case 11:
                player.amc.getAction(AttackAction.class).setExtraAttacks(2);
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
