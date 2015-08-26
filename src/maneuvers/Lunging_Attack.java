package maneuvers;

import events.attack.AttackDamageRollEvent;
import events.attack.AttackFinishEvent;
import events.attack.AttackTargetEvent;
import player.Player;
import queries.BooleanQuery;
import queries.Query;
import rounds.InitiativeOrder;

public class Lunging_Attack extends Maneuver {

    public boolean active;

    public Lunging_Attack(Player player, ManeuversComponent mc) {
        super(player, mc);

        add(AttackTargetEvent.class, e -> {
            if (InitiativeOrder.io.current().creature == player) {
                if (e.creature == player) {
                    if (mc.diceUsed < mc.diceCap) {
                        if (Query.ask(player, new BooleanQuery("Use the Lunging Atttack maneuver?")).response) {
                            e.range += 5;
                            mc.diceUsed--;
                            active = true;
                            mc.moddingAttack = true;
                        }
                    }
                }
            }
        });
        add(AttackFinishEvent.class, e -> {
            if (active) {
                active = mc.moddingAttack = false;
            }
        });
        add(AttackDamageRollEvent.class, e -> {
            if (e.a.attacker == player) {
                if (active) {
                    mc.addDieTo(e.a.damage);
                }
            }
        });
    }
}
