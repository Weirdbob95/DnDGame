package classes;

import actions.Action;
import static actions.Action.Type.BONUS_ACTION;
import actions.AttackAction;
import amounts.Die;
import creature.Creature;
import enums.FightingStyle;
import events.*;
import events.attack.AttackResultEvent;
import player.FightingStylesComponent;
import player.Player;
import queries.PointBuyQuery;
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

    public FightingStyle fightingStyle;
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
                fightingStyle = Query.ask(player, new SelectQuery<FightingStyle>("Choose a fighting style", FightingStyle.values())).response;
                player.getComponent(FightingStylesComponent.class).addFightingStyle(fightingStyle);
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
                                if (are.a.isWeapon && !are.a.isCritical && are.a.roll == 19) {
                                    are.a.isCritical = true;
                                }
                            }
                        };
                        break;
                }
                break;
            case 4:
                player.asc.setAll(Query.ask(player, new PointBuyQuery(2, player.asc.getAll(), new int[]{20, 20, 20, 20, 20, 20})).response);
                break;
            case 5:
                if (player.amc.getAction(AttackAction.class).attacks < 2) {
                    player.amc.getAction(AttackAction.class).attacks = 2;
                }
                break;
            case 6:
                player.asc.setAll(Query.ask(player, new PointBuyQuery(2, player.asc.getAll(), new int[]{20, 20, 20, 20, 20, 20})).response);
                break;
            case 7:
                switch (archetype) {
                    case Champion:
                        break;
                }
            case 8:
                player.asc.setAll(Query.ask(player, new PointBuyQuery(2, player.asc.getAll(), new int[]{20, 20, 20, 20, 20, 20})).response);
                break;
        }
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
            creature.hc.heal(new Die(10).roll + level);
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
