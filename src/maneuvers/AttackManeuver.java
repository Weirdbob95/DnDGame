package maneuvers;

import events.Event;
import events.attack.AttackDamageRollEvent;
import player.Player;

public abstract class AttackManeuver extends Maneuver {

    public AttackManeuver(Player player, ManeuversComponent mc) {
        super(player, mc);
    }

    @Override
    public Class<? extends Event>[] callOn() {
        return new Class[0];
    }

    @Override
    public void onEvent(Event e) {
    }

    public abstract void use(AttackDamageRollEvent adre);
}
