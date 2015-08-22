package classes.fighter;

import amounts.Amount;
import classes.Archetype;
import java.util.ArrayList;
import java.util.Arrays;
import queries.Query;
import queries.SelectQuery;
import util.Selectable;

public class Battle_Master extends Archetype<Fighter> {

    public ArrayList<Maneuver> maneuvers;
    public int diceUsed;
    public Amount diceCap = () -> {
        if (level() < 7) {
            return 4;
        }
        if (level() < 15) {
            return 5;
        }
        return 6;
    };
    public Amount dieSize = () -> {
        if (level() < 10) {
            return 8;
        }
        if (level() < 18) {
            return 10;
        }
        return 12;
    };

    public Battle_Master(Fighter playerClass) {
        super(playerClass);
        maneuvers = new ArrayList();
    }

    public void add(Maneuver m) {
        maneuvers.add(m);
        switch (m) {
            case Commander__s_Strike:
                break;
        }
    }

    @Override
    public void levelUp(int newLevel) {
        switch (newLevel) {
            case 3:
                for (int i = 0; i < 3; i++) {
                    add(Query.ask(player(), new SelectQuery<Maneuver>("Choose a maneuver to learn", remainingManeuvers())).response);
                }
                break;
        }
    }

    public ArrayList<Maneuver> remainingManeuvers() {
        ArrayList<Maneuver> options = new ArrayList(Arrays.asList(Maneuver.values()));
        options.removeAll(maneuvers);
        return options;
    }

    public enum Maneuver implements Selectable {

        Commander__s_Strike(false, "You can forgo one of your attacks and use a bonus action to direct one of your companions to strike."),
        Disarming_Attack(true, "You attempt to disarm your target, forcing it to drop one item of your choice that it's holding."),
        Evasive_Footwork(false, "When you move, you can expend one superiority die, rolling the die and adding the number rolled to your AC.");

        public final boolean isAttackEffect;
        private final String desc;

        private Maneuver(boolean isAttackEffect, String desc) {
            this.isAttackEffect = isAttackEffect;
            this.desc = desc;
        }

        @Override
        public String getDescription() {
            return desc;
        }

        @Override
        public String getName() {
            return name().replace("__", "'").replace("_", " ");
        }
    }
}
