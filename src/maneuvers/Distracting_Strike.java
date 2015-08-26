package maneuvers;

import creature.Creature;
import events.TurnStartEvent;
import events.attack.AttackDamageRollEvent;
import events.attack.AttackRollEvent;
import player.Player;

public class Distracting_Strike extends AttackManeuver {

    public Creature target;

    public Distracting_Strike(Player player, ManeuversComponent mc) {
        super(player, mc);

        add(TurnStartEvent.class, e -> target = (e.creature == player ? null : target));
        add(AttackRollEvent.class, e -> {
            if (e.a.target == target && e.a.attacker != player) {
                e.a.advantage = true;
                target = null;
            }
        });
    }

    @Override
    public void use(AttackDamageRollEvent adre) {
        mc.addDieTo(adre.a.damage);
        target = adre.a.target;
    }
}
