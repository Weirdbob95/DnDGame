package maneuvers;

import actions.Action;
import static actions.Action.Type.BONUS_ACTION;
import creature.Creature;
import events.TurnEndEvent;
import events.attack.AttackRollEvent;
import grid.Square;
import player.Player;
import queries.Query;
import queries.SquareQuery;

public class Feinting_Attack extends Maneuver {

    public Creature target;

    public Feinting_Attack(Player player, ManeuversComponent mc) {
        super(player, mc);

        add(TurnEndEvent.class, e -> target = (e.creature == player ? null : target));
        add(AttackRollEvent.class, e -> {
            if (e.a.attacker == player && e.a.target == target) {
                e.a.advantage = true;
                mc.addDieTo(e.a.damage);
                target = null;
            }
        });
    }

    @Override
    public void forget() {
        super.forget();
        player.amc.removeAction(Feinting_AttackAction.class);
    }

    @Override
    public void learn() {
        super.learn();
        player.amc.addAction(new Feinting_AttackAction(player));
    }

    public class Feinting_AttackAction extends Action {

        public Feinting_AttackAction(Creature creature) {
            super(creature);
        }

        @Override
        protected void act() {
            Square s = Query.ask(creature, new SquareQuery("Choose a creature", creature.glc.occupied, 5, true)).response;
            if (s != null && s.creature != player) {
                target = s.creature;
                mc.diceUsed++;
            }
        }

        @Override
        public String[] defaultTabs() {
            return new String[]{};
        }

        @Override
        public Type getType() {
            return BONUS_ACTION;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public boolean isAvailable() {
            return mc.diceUsed < mc.diceCap;
        }
    }
}
