package races;

import amounts.Die;
import enums.Size;
import events.AbilityCheckResultEvent;
import events.SavingThrowResultEvent;
import events.attack.AttackResultEvent;
import player.Player;
import queries.Query;
import queries.SelectQuery;
import util.Selectable;

public class Halfling extends Race {

    public enum Subrace implements Selectable {

        Lightfoot_Halfling("As a lightfoot halfling, you can easily hide from notice, even using other people as cover."),
        Stout_Halfling("As a stout halfling, you're hardier than average and have some resistance to poison.");

        public final String description;

        private Subrace(String description) {
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

    public Subrace subrace;

    public Halfling(Player player) {
        super(player);
    }

    @Override
    public int[] getAbilityScores() {
        switch (subrace) {
            case Lightfoot_Halfling:
                return new int[]{0, 2, 0, 0, 0, 1};
            case Stout_Halfling:
                return new int[]{0, 2, 1, 0, 0, 0};
        }
        return null;
    }

    @Override
    public int getSpeed() {
        return 25;
    }

    @Override
    public void init() {
        subrace = Query.ask(player, new SelectQuery<Subrace>("Choose your character's subrace", Subrace.values())).response;
        super.init();
        add(AttackResultEvent.class, -1, e -> {
            if (e.a.attacker == player && e.a.roll == 1) {
                e.a.roll = new Die(20).roll;
            }
        });
        add(AbilityCheckResultEvent.class, -1, e -> {
            if (e.ace.creature == player && e.ace.roll == 1) {
                e.ace.roll = new Die(20).roll;
            }
        });
        add(SavingThrowResultEvent.class, -1, e -> {
            if (e.ste.creature == player && e.ste.roll == 1) {
                e.ste.roll = new Die(20).roll;
            }
        });
    }

    @Override
    public String[] languages() {
        return new String[]{"Common", "Halfling"};
    }

    @Override
    public Size size() {
        return Size.SMALL;
    }
}
