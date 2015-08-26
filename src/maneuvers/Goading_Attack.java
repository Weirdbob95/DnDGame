package maneuvers;

import creature.Creature;
import static enums.AbilityScore.WIS;
import events.SavingThrowEvent;
import events.TurnEndEvent;
import events.TurnStartEvent;
import events.attack.AttackDamageRollEvent;
import events.attack.AttackRollEvent;
import player.Player;

public class Goading_Attack extends AttackManeuver {

    public Creature target;
    public boolean isNextTurn;

    public Goading_Attack(Player player, ManeuversComponent mc) {
        super(player, mc);

        add(TurnStartEvent.class, e -> isNextTurn = (e.creature == player ? true : isNextTurn));
        add(TurnEndEvent.class, e -> target = (e.creature == player && isNextTurn ? null : target));
        add(AttackRollEvent.class, e -> {
            if (e.a.attacker == target && e.a.target != player) {
                e.a.disadvantage = true;
            }
        });
    }

    @Override
    public void use(AttackDamageRollEvent adre) {
        mc.addDieTo(adre.a.damage);
        if (!new SavingThrowEvent(adre.a.target, WIS, mc.DC.get()).success()) {
            target = adre.a.target;
            isNextTurn = false;
        }
    }
}
