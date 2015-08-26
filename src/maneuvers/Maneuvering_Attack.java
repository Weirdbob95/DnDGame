package maneuvers;

import static actions.Action.Type.REACTION;
import creature.Creature;
import events.attack.AttackDamageRollEvent;
import grid.Square;
import player.Player;
import queries.BooleanQuery;
import queries.Query;
import queries.SquareQuery;

public class Maneuvering_Attack extends AttackManeuver {

    public Maneuvering_Attack(Player player, ManeuversComponent mc) {
        super(player, mc);
    }

    @Override
    public void use(AttackDamageRollEvent adre) {
        mc.addDieTo(adre.a.damage);
        Square toHelp = Query.ask(player, new SquareQuery("Choose a creature to give an attack to", (Square) null, -1, true)).response;
        if (toHelp != null) {
            Creature ally = toHelp.creature;
            if (ally != null && ally != player) {
                if (ally.amc.available.contains(REACTION)) {
                    if (Query.ask(ally, new BooleanQuery("Move without provoking opportunity attacks?")).response) {
                        ally.amc.available.remove(REACTION);
                    }
                }
            }
        }
    }
}
