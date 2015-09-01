package maneuvers;

import events.attack.AttackDamageRollEvent;
import player.Player;

public abstract class AttackManeuver extends Maneuver {

    public AttackManeuver(Player player, ManeuversComponent mc) {
        super(player, mc);
    }

    public abstract void use(AttackDamageRollEvent e);
}
