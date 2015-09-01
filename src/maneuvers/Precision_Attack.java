package maneuvers;

import events.attack.AttackFinishEvent;
import events.attack.AttackResultEvent;
import player.Player;
import queries.BooleanQuery;
import queries.Query;

public class Precision_Attack extends Maneuver {

    public boolean active;

    public Precision_Attack(Player player, ManeuversComponent mc) {
        super(player, mc);

        add(AttackResultEvent.class, e -> {
            if (e.a.attacker == player) {
                if (!mc.moddingAttack) {
                    if (mc.diceUsed < mc.diceCap) {
                        if (Query.ask(player, new BooleanQuery("Use the Precision Attack maneuver?")).response) {
                            mc.addDieTo(e.a.toHit);
                            mc.diceUsed++;
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
    }
}
