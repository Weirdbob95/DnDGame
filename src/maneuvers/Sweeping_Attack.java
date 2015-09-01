package maneuvers;

import amounts.Die;
import events.attack.AttackDamageRollEvent;
import grid.Square;
import player.Player;
import queries.Query;
import queries.SquareQuery;

public class Sweeping_Attack extends AttackManeuver {

    public Sweeping_Attack(Player player, ManeuversComponent mc) {
        super(player, mc);
    }

    @Override
    public void use(AttackDamageRollEvent e) {
        Square toAttack = Query.ask(player, new SquareQuery("Choose a creature to damage", player.glc.occupied, e.a.range, true)).response;
        if (toAttack != null && toAttack.creature != null) {
            if (toAttack.creature != e.a.target) {
                if (e.a.roll + e.a.toHit.get() >= toAttack.creature.ac.AC.get()) {
                    toAttack.creature.hc.damage(new Die(mc.dieSize).roll);
                }
            }
        }
    }
}
