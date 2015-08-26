package maneuvers;

import creature.Creature;
import events.Event;
import events.TurnStartEvent;
import events.attack.AttackDamageRollEvent;
import events.attack.AttackRollEvent;
import player.Player;

public class Distracting_Strike extends AttackManeuver {

    public Creature target;

    public Distracting_Strike(Player player, ManeuversComponent mc) {
        super(player, mc);
    }

    @Override
    public Class<? extends Event>[] callOn() {
        return new Class[]{TurnStartEvent.class, AttackRollEvent.class};
    }

    @Override
    public void onEvent(Event e) {
        if (target != null) {
            if (e instanceof TurnStartEvent) {
                target = null;
            } else {
                AttackRollEvent are = (AttackRollEvent) e;
                if (are.a.target == target && are.a.attacker != player) {
                    are.a.advantage = true;
                    target = null;
                }
            }
        }
    }

    @Override
    public void use(AttackDamageRollEvent adre) {
        mc.addDieTo(adre.a.damage);
        target = adre.a.target;
    }
}
