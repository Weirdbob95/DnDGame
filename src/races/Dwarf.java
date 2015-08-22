package races;

import player.Player;
import queries.Query;
import queries.SelectQuery;
import util.Selectable;

public class Dwarf extends Race {

    public enum Subrace implements Selectable {

        Hill_Dwarf("As a hill dwarf, you have keen senses, deep intuition, and remarkable resilience."),
        Mountain_Dwarf("As a mountain dwarf, you're strong and hardy, accustomed to a difficult life in rugged terrain.");

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
            case Hill_Dwarf:
                player.hc.maxHealth.set("Dwarven Toughness", () -> player.clc.level());
                break;
            case Mountain_Dwarf:
                break;
        }
    }

    @Override
    public int[] getAbilityScores() {
        switch (subrace) {
            case Hill_Dwarf:
                return new int[]{0, 0, 2, 0, 1, 0};
            case Mountain_Dwarf:
                return new int[]{2, 0, 2, 0, 0, 0};
        }
        return null;
    }

    @Override
    public int getSpeed() {
        return 25;
    }

    @Override
    public String[] languages() {
        return new String[]{"Common", "Dwarvish"};
    }
}
