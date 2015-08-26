package maneuvers;

import actions.Action;
import static actions.Action.Type.BONUS_ACTION;
import creature.Creature;
import events.Event;
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
    }

    @Override
    public Class<? extends Event>[] callOn() {
        return new Class[]{TurnEndEvent.class, AttackRollEvent.class};
    }

    @Override
    public void forget() {
        super.forget();
        player.amc.actions.removeIf(a -> a instanceof Feinting_AttackAction);
    }

    @Override
    public void learn() {
        super.learn();
        player.amc.actions.add(new Feinting_AttackAction(player));
    }

    @Override
    public void onEvent(Event e) {
        if (target != null) {
            if (e instanceof TurnEndEvent) {
                target = null;
            } else if (e instanceof AttackRollEvent) {
                AttackRollEvent are = (AttackRollEvent) e;
                if (are.a.attacker == player && are.a.target == target) {
                    are.a.advantage = true;
                    mc.addDieTo(are.a.damage);
                    target = null;
                }
            }
        }
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
    }
}
