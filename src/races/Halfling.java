package races;

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

    @Override
    public void addTo(Player player) {
        subrace = Query.ask(player, new SelectQuery<Subrace>("Choose your character's subrace", Subrace.values())).response;
        super.addTo(player);
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
}
