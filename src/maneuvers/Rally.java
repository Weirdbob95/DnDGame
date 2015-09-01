package maneuvers;

import actions.Action;
import static actions.Action.Type.BONUS_ACTION;
import amounts.Die;
import creature.Creature;
import static enums.AbilityScore.CHA;
import grid.Square;
import player.Player;
import queries.Query;
import queries.SquareQuery;

public class Rally extends Maneuver {

    public Rally(Player player, ManeuversComponent mc) {
        super(player, mc);
    }

    @Override
    public void forget() {
        super.forget();
        player.amc.removeAction(RallyAction.class);
    }

    @Override
    public void learn() {
        super.learn();
        player.amc.addAction(new RallyAction(player));
    }

    public class RallyAction extends Action {

        public RallyAction(Creature creature) {
            super(creature);
        }

        @Override
        protected void act() {
            Square s = Query.ask(creature, new SquareQuery("Choose a creature", creature.glc.occupied, 5, true)).response;
            if (s != null && s.creature != player) {
                s.creature.hc.giveTempHP(new Die(mc.dieSize).roll + player.asc.mod(CHA).get());
            }
        }

        @Override
        public String[] defaultTabs() {
            return new String[]{};
        }

        @Override
        public Action.Type getType() {
            return BONUS_ACTION;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
