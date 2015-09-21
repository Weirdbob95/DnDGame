package maneuvers;

import conditions.Frightened;
import creature.Creature;
import static enums.AbilityScore.WIS;
import events.SavingThrowEvent;
import events.TurnEndEvent;
import events.TurnStartEvent;
import events.attack.AttackDamageRollEvent;
import player.Player;
import util.Mutable;

public class Menacing_Attack extends AttackManeuver {

    public Creature target;

    public Menacing_Attack(Player player, ManeuversComponent mc) {
        super(player, mc);
    }

    @Override
    public void use(AttackDamageRollEvent e) {
        mc.addDieTo(e.a.damage);
        if (SavingThrowEvent.fail(e.a.target, WIS, mc.DC.get())) {
            Frightened f = new Frightened(e.a.target, player);
            if (f.add()) {
                Mutable<Boolean> isNextTurn = new Mutable(false);
                f.add(TurnStartEvent.class, ev -> isNextTurn.o = (ev.creature == player ? true : isNextTurn.o));
                f.add(TurnEndEvent.class, ev -> {
                    if (ev.creature == player && isNextTurn.o) {
                        f.remove();
                    }
                });
            }
        }
    }
}
