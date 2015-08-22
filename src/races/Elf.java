package races;

import player.Player;
import queries.Query;
import queries.SelectQuery;
import util.Selectable;

public class Elf extends Race {

    public enum Subrace implements Selectable {

        High_Elf("As a high elf, you have a keen mind and a mastery of at least the basic of magic."),
        Wood_Elf("As a wood elf, you have keen senses and intuition, and your fleet feet carry you quickly and stealthily through your native forests."),
        Drow("The drow were banished from the surface world for following the goddess Lolth down the path of evil and corruption.");

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
        switch (subrace) {
            case High_Elf:
                player.lc.chooseLanguage();
        }
    }

    @Override
    public int[] getAbilityScores() {
        switch (subrace) {
            case High_Elf:
                return new int[]{0, 2, 0, 1, 0, 0};
            case Wood_Elf:
                return new int[]{0, 2, 0, 0, 1, 0};
            case Drow:
                return new int[]{0, 2, 0, 0, 0, 1};
        }
        return null;
    }

    @Override
    public int getSpeed() {
        if (subrace == Subrace.Wood_Elf) {
            return 35;
        }
        return 30;
    }

    @Override
    public String[] languages() {
        return new String[]{"Common", "Elvish"};
    }
}
