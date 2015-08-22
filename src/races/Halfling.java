package races;

import amounts.Die;
import enums.Size;
import events.AbilityCheckResultEvent;
import events.AbstractEventListener;
import events.Event;
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

    @Override
    public void addTo(Player player) {
        subrace = Query.ask(player, new SelectQuery<Subrace>("Choose your character's subrace", Subrace.values())).response;
        super.addTo(player);
        new AbstractEventListener(player) {
            @Override
            public Class<? extends Event>[] callOn() {
                return new Class[]{AttackResultEvent.class, AbilityCheckResultEvent.class, SavingThrowResultEvent.class};
            }

            @Override
            public void onEvent(Event e) {
                if (e instanceof AttackResultEvent) {
                    AttackResultEvent are = (AttackResultEvent) e;
                    if (are.a.attacker == player && are.a.roll == 1) {
                        are.a.roll = new Die(20).roll;
                    }
                } else if (e instanceof AbilityCheckResultEvent) {
                    AbilityCheckResultEvent acre = (AbilityCheckResultEvent) e;
                    if (acre.ace.creature == player && acre.ace.roll == 1) {
                        acre.ace.roll = new Die(20).roll;
                    }
                } else if (e instanceof SavingThrowResultEvent) {
                    SavingThrowResultEvent stre = (SavingThrowResultEvent) e;
                    if (stre.ste.creature == player && stre.ste.roll == 1) {
                        stre.ste.roll = new Die(20).roll;
                    }
                }
            }
        };
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
    public String[] languages() {
        return new String[]{"Common", "Halfling"};
    }

    @Override
    public Size size() {
        return Size.SMALL;
    }
}
