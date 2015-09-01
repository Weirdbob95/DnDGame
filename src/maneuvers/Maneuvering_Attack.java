package maneuvers;

import static actions.Action.Type.REACTION;
import creature.Creature;
import events.attack.AttackDamageRollEvent;
import events.attack.OpportunityAttackEvent;
import events.move.PathMoveEvent;
import grid.Square;
import player.Player;
import queries.BooleanQuery;
import queries.Query;
import queries.SquareQuery;

public class Maneuvering_Attack extends AttackManeuver {

    public Creature ally;
    public Creature target;

    public Maneuvering_Attack(Player player, ManeuversComponent mc) {
        super(player, mc);

        add(OpportunityAttackEvent.class, e -> {
            if (e.a.attacker == target && e.a.target == ally) {
                e.blockers.add("Maneuvering Attack");
            }
        });
    }

    @Override
    public void use(AttackDamageRollEvent e) {
        mc.addDieTo(e.a.damage);
        Square toHelp = Query.ask(player, new SquareQuery("Choose a creature to give an attack to", (Square) null, -1, true)).response;
        if (toHelp != null) {
            ally = toHelp.creature;
            if (ally != null && ally != player) {
                if (ally.amc.hasType(REACTION)) {
                    if (Query.ask(ally, new BooleanQuery("Move without provoking opportunity attacks from a creature?")).response) {
                        target = e.a.target;
                        ally.amc.useType(REACTION, this);
                        new PathMoveEvent(ally, ally.spc.landSpeed.get() / 2).call();
                        target = null;
                    }
                }
            }
            ally = null;
        }
    }
}
