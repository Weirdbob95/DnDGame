package maneuvers;

import creature.Creature;
import static enums.AbilityScore.WIS;
import events.Event;
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
    }

    @Override
    public Class<? extends Event>[] callOn() {
        return new Class[]{TurnStartEvent.class, TurnEndEvent.class, AttackRollEvent.class};
    }

    @Override
    public void onEvent(Event e) {
        if (target != null) {
            if (e instanceof TurnStartEvent) {
                isNextTurn = true;
            } else if (e instanceof TurnEndEvent) {
                if (isNextTurn) {
                    target = null;
                }
            } else {
                AttackRollEvent are = (AttackRollEvent) e;
                if (are.a.attacker == target && are.a.target != player) {
                    are.a.disadvantage = true;
                }
            }
        }
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
