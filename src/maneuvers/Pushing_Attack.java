package maneuvers;

import static enums.AbilityScore.STR;
import static enums.Size.LARGE;
import events.SavingThrowEvent;
import events.attack.AttackDamageRollEvent;
import events.move.TeleportEvent;
import player.Player;

public class Pushing_Attack extends AttackManeuver {

    public Pushing_Attack(Player player, ManeuversComponent mc) {
        super(player, mc);
    }

    @Override
    public void use(AttackDamageRollEvent e) {
        mc.addDieTo(e.a.damage);
        if (e.a.target.cdc.size.squares <= LARGE.squares) {
            if (SavingThrowEvent.fail(e.a.target, STR, mc.DC.get())) {
                TeleportEvent.pushAway(player, e.a.target, 15);
            }
        }
    }
}
