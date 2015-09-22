package classes.monk;

import actions.*;
import static actions.Action.Type.*;
import amounts.*;
import classes.PlayerClass;
import conditions.Charmed;
import conditions.Frightened;
import conditions.Invisible;
import conditions.Stunned;
import creature.Creature;
import enums.AbilityScore;
import static enums.AbilityScore.*;
import enums.Skill;
import static enums.Skill.*;
import events.*;
import events.attack.AttackDamageResultEvent;
import events.attack.AttackDamageRollEvent;
import events.attack.AttackEvent;
import events.attack.AttackTargetEvent;
import items.Weapon;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import player.KiComponent;
import player.Player;
import queries.BooleanQuery;
import queries.Query;
import queries.SelectQuery;
import util.Selectable;
import util.SelectableImpl;

public class Monk extends PlayerClass {

    public KiComponent kc;
    public Amount kiSaveDC = new AddedAmount(new Value(8), player.pc.prof, player.asc.mod(WIS));

    public Monk(Player player) {
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

    public static boolean isMonkWeapon(Weapon w) {
        return w.name.equals("Monk Weapon") || w.name.equals("Shortsword")
                || (w.isSimple && !w.isRanged && !w.heavy && !w.two_handed);
    }

    @Override
    public void levelUp(int newLevel) {
        switch (newLevel) {
            case 1:
                player.ac.AC.set("Base", new ConditionalAmount(() -> player.ac.armor == null,
                        new AddedAmount(new Value(10), player.asc.mod(DEX), player.asc.mod(WIS)), player.ac.AC.components.get("Base")));

                add(AttackEvent.class, e -> {
                    if (e.attacker == player) {
                        if (e.isWeapon && isMonkWeapon(e.weapon)) {
                            e.allowedAbilityScores.add(DEX);
                        }
                    }
                });
                Supplier<Value> martialArts = (Supplier<Value> & Serializable) () -> new Value(new Die(((level + 13) / 6) * 2));
                add(AttackDamageRollEvent.class, e -> {
                    if (e.a.attacker == player) {
                        if (e.a.damage.components.get("Weapon").asValue().average() < martialArts.get().average()) {
                            e.a.damage.set("Base", martialArts.get());
                        }
                    }
                });
                player.amc.addAction(new Martial_Arts(player));
                break;
            case 2:
                kc = player.getComponent(KiComponent.class);
                if (kc == null) {
                    kc = player.add(new KiComponent(player));
                }
                kc.maximumKi.set("Monk", () -> level);

                player.amc.addAction(new Flurry_of_Blows(player));
                player.amc.addAction(new Patient_Defense(player));
                player.amc.addAction(new Step_of_the_Wind(player));

                player.spc.landSpeed.flatComponents.put("Unarmored Movement",
                        new ConditionalAmount(() -> player.ac.armor == null, () -> ((level + 6) / 4) * 5));
                break;
            case 3:
                add(AttackDamageResultEvent.class, 1, e -> {
                    if (e.a.target == player) {
                        if (e.a.isWeapon && e.a.isRanged) {
                            if (player.amc.hasType(REACTION)) {
                                if (Query.ask(player, new BooleanQuery("Attempt to deflect the missile?")).response) {
                                    player.amc.useType(REACTION, "Deflect Missiles");
                                    e.a.damage.set("Deflect Missiles", -(new Die(10).roll + player.asc.mod(DEX).get() + level));
                                    if (e.a.damage.get() < 0) {
                                        if (player.wc.countHands(null) >= 1) {
                                            if (kc.getKi() >= 1) {
                                                if (Query.ask(player, new BooleanQuery("Throw the missile at another creature?")).response) {
                                                    kc.useKi(1);
                                                    Weapon w = new Weapon();
                                                    w.name = "Monk Weapon";
                                                    w.isRanged = true;
                                                    new AttackTargetEvent(player, w, 60, "Deflect Missiles").call();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
                break;
            case 5:
                player.amc.getAction(AttackAction.class).setExtraAttacks(1);

                add(AttackDamageRollEvent.class, e -> {
                    if (e.a.attacker == player) {
                        if (e.a.isWeapon && !e.a.isRanged) {
                            if (kc.getKi() >= 1) {
                                if (Query.ask(player, new BooleanQuery("Make a Stunning Strike?")).response) {
                                    kc.useKi(1);
                                    if (SavingThrowEvent.fail(e.a.target, CON, kiSaveDC.get())) {
                                        new Stunned(e.a.target, this).add();
                                    }
                                }
                            }
                        }
                    }
                });
                break;
            case 6:
                //ki strikes
                break;
            case 7:
                player.amc.addAction(new Stillness_of_Mind(player));
                break;
            case 14:
                player.pc.savingThrowProfs.addAll(Arrays.asList(AbilityScore.values()));
                add(SavingThrowResultEvent.class, e -> {
                    if (e.ste.creature == player) {
                        if (kc.getKi() >= 1) {
                            if (!e.ste.success()) {
                                if (Query.ask(player, new BooleanQuery("Use the Diamond Soul ability?")).response) {
                                    e.ste.roll = new Die(20).roll;
                                    kc.useKi(1);
                                }
                            }
                        }
                    }
                });
                break;
            case 18:
                player.amc.addAction(new Empty_Body(player));
                break;
        }
    }

    @Override
    public AbilityScore[] savingThrows() {
        return new AbilityScore[]{STR, DEX};
    }

    @Override
    public Skill[] skills() {
        return new Skill[]{Acrobatics, Athletics, History, Insight, Religion, Stealth};
    }

    public class Empty_Body extends Action {

        public int turnsRemaining;

        public Empty_Body(Creature creature) {
            super(creature);

            add(TurnStartEvent.class, e -> {
                if (turnsRemaining-- == 0) {
                    creature.cnc.remove(Invisible.class, this);
                }
            });
        }

        @Override
        protected void act() {
            kc.useKi(4);
            turnsRemaining = 10;
            new Invisible(creature, this).add();
        }

        @Override
        public String[] defaultTabs() {
            return new String[]{};
        }

        @Override
        public String getDescription() {
            return "You become invisible and have resistance to all damage but force damage for 1 minute.";
        }

        @Override
        public Type getType() {
            return ACTION;
        }

        @Override
        public boolean isAvailable() {
            return kc.getKi() >= 4;
        }
    }

    public class Flurry_of_Blows extends Action {

        public boolean available;

        public Flurry_of_Blows(Creature creature) {
            super(creature);

            add(UseActionEvent.class, e -> {
                if (e.action.creature == creature) {
                    if (e.action instanceof AttackAction) {
                        available = true;
                    }
                }
            });
            add(TurnStartEvent.class, e -> {
                if (e.creature == creature) {
                    available = false;
                }
            });
        }

        @Override
        protected void act() {
            available = false;
            kc.useKi(1);

            Weapon w = creature.wc.unarmedStrike;
            int range = Math.max(w.range, creature.cdc.reach.get() + (w.reach ? 5 : 0));
            new AttackTargetEvent(creature, w, range, this).call();

            while (true) {
                ArrayList<Selectable> choices = new ArrayList();
                choices.add(new SelectableImpl("Extra Attack", "Make another attack (as part of the Attack Action)"));
                if (creature.amc.getAction(MoveAction.class).isAvailable()) {
                    choices.add(creature.amc.getAction(MoveAction.class));
                }
                Selectable next = Query.ask(creature, new SelectQuery("Choose an action", choices, "Use Action", "Cancel")).response;
                if (next == null) {
                    break;
                }
                if (next.getName().equals("Extra Attack")) {
                    new AttackTargetEvent(creature, w, range, this).call();
                    break;
                } else if (next instanceof MoveAction) {
                    ((MoveAction) next).use();
                }
            }
        }

        @Override
        public String[] defaultTabs() {
            return new String[]{};
        }

        @Override
        public String getDescription() {
            return "Spend 1 ki point to make two unarmed strikes as a bonus action.";
        }

        @Override
        public Type getType() {
            return BONUS_ACTION;
        }

        @Override
        public boolean isAvailable() {
            return available && kc.getKi() >= 1;
        }
    }

    public class Martial_Arts extends Action {

        public boolean available;

        public Martial_Arts(Creature creature) {
            super(creature);

            add(AttackEvent.class, e -> {
                if (e.attacker == creature) {
                    if (e.source instanceof AttackAction) {
                        if (e.isWeapon) {
                            if (isMonkWeapon(e.weapon)) {
                                available = true;
                            }
                        }
                    }
                }
            });
            add(TurnStartEvent.class, e -> {
                if (e.creature == creature) {
                    available = false;
                }
            });
        }

        @Override
        protected void act() {
            available = false;
            kc.useKi(1);

            Weapon w = creature.wc.unarmedStrike;
            int range = Math.max(w.range, creature.cdc.reach.get() + (w.reach ? 5 : 0));
            new AttackTargetEvent(creature, w, range, this).call();
        }

        @Override
        public String[] defaultTabs() {
            return new String[]{};
        }

        @Override
        public String getDescription() {
            return "Make one unarmed strike as a bonus action.";
        }

        @Override
        public Action.Type getType() {
            return BONUS_ACTION;
        }

        @Override
        public boolean isAvailable() {
            return available;
        }
    }

    public class Patient_Defense extends Action {

        public Patient_Defense(Creature creature) {
            super(creature);
        }

        @Override
        protected void act() {
            kc.useKi(1);
            creature.amc.getAction(Dodge.class).useNoType();
        }

        @Override
        public String[] defaultTabs() {
            return new String[]{};
        }

        @Override
        public String getDescription() {
            return "Spend 1 ki point to take the Dodge action as a bonus action.";
        }

        @Override
        public Type getType() {
            return BONUS_ACTION;
        }

        @Override
        public boolean isAvailable() {
            return kc.getKi() >= 1;
        }
    }

    public class Step_of_the_Wind extends Action {

        public Step_of_the_Wind(Creature creature) {
            super(creature);
        }

        @Override
        protected void act() {
            kc.useKi(1);
            Query.ask(creature, new SelectQuery<Action>("Choose which action to use+", creature.amc.getAction(Disengage.class), creature.amc.getAction(Dash.class))).response.useNoType();
        }

        @Override
        public String[] defaultTabs() {
            return new String[]{};
        }

        @Override
        public String getDescription() {
            return "Spend 1 ki point to take the Disengage or Dash action as a bonus action, and your jump distance is doubled for your turn.";
        }

        @Override
        public Type getType() {
            return BONUS_ACTION;
        }

        @Override
        public boolean isAvailable() {
            return kc.getKi() >= 1;
        }
    }

    public class Stillness_of_Mind extends Action {

        public Stillness_of_Mind(Creature creature) {
            super(creature);
        }

        @Override
        protected void act() {
            Set effects = creature.cnc.getConditions(Charmed.class).keySet();
            effects.addAll(creature.cnc.getConditions(Frightened.class).keySet());
            Stream<Selectable> choices = effects.stream().map(o -> o instanceof Selectable ? (Selectable) o
                    : (Selectable) new SelectableImpl(o.toString(), o.toString()));
            Selectable chosen = Query.ask(creature, new SelectQuery("Choose a condition to end", choices.collect(Collectors.toList()))).response;
            Object effect = chosen;
            if (effect instanceof SelectableImpl) {
                effect = effects.stream().filter(o -> chosen.getDescription().equals(o.toString())).findAny().orElse(chosen);
            }
            creature.cnc.remove(effect);
        }

        @Override
        public String[] defaultTabs() {
            return new String[]{};
        }

        @Override
        public String getDescription() {
            return "End one effect on yourself that is causing you to be charmed or frightened.";
        }

        @Override
        public Type getType() {
            return ACTION;
        }

        @Override
        public boolean isAvailable() {
            return !creature.cnc.getConditions(Charmed.class).isEmpty() || !creature.cnc.getConditions(Frightened.class).isEmpty();
        }
    }
}
