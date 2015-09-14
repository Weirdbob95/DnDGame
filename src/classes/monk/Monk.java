package classes.monk;

import actions.Action;
import static actions.Action.Type.BONUS_ACTION;
import actions.AttackAction;
import actions.MoveAction;
import amounts.AddedAmount;
import amounts.ConditionalAmount;
import amounts.Die;
import amounts.Value;
import classes.PlayerClass;
import creature.Creature;
import enums.AbilityScore;
import static enums.AbilityScore.*;
import enums.Skill;
import static enums.Skill.*;
import events.FinishActionEvent;
import events.TurnStartEvent;
import events.UseActionEvent;
import events.attack.AttackDamageRollEvent;
import events.attack.AttackEvent;
import events.attack.AttackTargetEvent;
import items.Weapon;
import java.util.ArrayList;
import java.util.function.Supplier;
import player.KiComponent;
import player.Player;
import queries.Query;
import queries.SelectQuery;
import util.Selectable;
import util.SelectableImpl;

public class Monk extends PlayerClass {

    public KiComponent kc;

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
        return w.name.equals("Shortsword") || (w.isSimple && !w.isRanged && !w.heavy && !w.two_handed);
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
                Supplier<Value> martialArts = () -> new Value(new Die(((level + 13) / 6) * 2));
                add(AttackDamageRollEvent.class, e -> {
                    if (e.a.attacker == player) {
                        if (e.a.damage.components.get("Base").asValue().average() < martialArts.get().average()) {
                            e.a.damage.set("Base", martialArts.get());
                        }
                    }
                });
                player.amc.addAction(new Martial_Arts(player));
                break;
            case 2:
                kc = player.getComponent(KiComponent.class);
                if (kc == null) {
                    kc = player.add(new KiComponent());
                }

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

            Weapon w = creature.wc.unarmedStrike;
            int range = Math.max(w.range, creature.cdc.reach.get() + (w.reach ? 5 : 0));
            new AttackTargetEvent(creature, w, range).call();

            for (int i = 1; i < 2;) {
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
                    new AttackTargetEvent(creature, w, range).call();
                    i++;
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
        public boolean duringAttackAction;

        public Martial_Arts(Creature creature) {
            super(creature);

            add(UseActionEvent.class, e -> {
                if (e.action.creature == creature) {
                    if (e.action instanceof AttackAction) {
                        available = true;
                    }
                }
            });
            add(FinishActionEvent.class, e -> {
                if (e.action.creature == creature) {
                    if (e.action instanceof AttackAction) {
                        available = false;
                    }
                }
            });
            add(AttackEvent.class, e -> {
                if (e.attacker == creature) {
                    if (duringAttackAction) {
                        if (e.isWeapon) {

                        }
                    }
                }
            });
            add(TurnStartEvent.class, e -> {
                if (e.creature == creature) {
                    available = false;
                }
            });

            add(UseActionEvent.class, e -> duringAttackAction = duringAttackAction || e.action instanceof AttackAction);
            add(FinishActionEvent.class, e -> duringAttackAction = duringAttackAction && !(e.action instanceof AttackAction));
            add(AttackEvent.class, e -> available = available || duringAttackAction && isMonkWeapon(e.weapon));
            add(TurnStartEvent.class, e -> available = false);
        }

        @Override
        protected void act() {
            available = false;

            Weapon w = creature.wc.unarmedStrike;
            int range = Math.max(w.range, creature.cdc.reach.get() + (w.reach ? 5 : 0));
            new AttackTargetEvent(creature, w, range).call();
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
}
