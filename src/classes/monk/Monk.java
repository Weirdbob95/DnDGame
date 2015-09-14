package classes.monk;

import actions.Action;
import static actions.Action.Type.BONUS_ACTION;
import actions.MoveAction;
import amounts.AddedAmount;
import amounts.ConditionalAmount;
import amounts.Value;
import classes.PlayerClass;
import creature.Creature;
import enums.AbilityScore;
import static enums.AbilityScore.DEX;
import static enums.AbilityScore.STR;
import static enums.AbilityScore.WIS;
import enums.Skill;
import static enums.Skill.Acrobatics;
import static enums.Skill.Athletics;
import static enums.Skill.History;
import static enums.Skill.Insight;
import static enums.Skill.Religion;
import static enums.Skill.Stealth;
import events.attack.AttackTargetEvent;
import items.Weapon;
import java.util.ArrayList;
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

    @Override
    public void levelUp(int newLevel) {
        switch (newLevel) {
            case 1:
                player.ac.AC.set("Base", new ConditionalAmount(() -> player.ac.armor == null,
                        new AddedAmount(new Value(10), player.asc.mod(DEX), player.asc.mod(WIS)), player.ac.AC.components.get("Base")));
                break;
            case 2:
                kc = player.add(new KiComponent());
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

    public class FlurryOfBlows extends Action {

        public FlurryOfBlows(Creature creature) {
            super(creature);
        }

        @Override
        protected void act() {
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
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Type getType() {
            return BONUS_ACTION;
        }

        public String getName() {
            return "Flurry of Blows";
        }

        @Override
        public String getDescription() {
            return "Immediately after you take the Attack action on your turn, you can spend 1 ki point to make two unarmed strikes as a bonus action";
        }

        public boolean isAvailable() {
            if (kc.getKi() >= 1) {
                return true;
            }
            return false;
        }
    }
}
