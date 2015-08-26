package maneuvers;

import events.Event;
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
    }

    @Override
    public Class<? extends Event>[] callOn() {
        return new Class[]{AttackTargetEvent.class, AttackFinishEvent.class, AttackDamageRollEvent.class};
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof AttackTargetEvent) {
            AttackTargetEvent ate = (AttackTargetEvent) e;
            if (InitiativeOrder.io.current().creature == player) {
                if (ate.creature == player) {
                    if (mc.diceUsed < mc.diceCap) {
                        if (Query.ask(player, new BooleanQuery("Use the Lunging Atttack maneuver?")).response) {
                            ate.range += 5;
                            mc.diceUsed--;
                            active = true;
                            mc.moddingAttack = true;
                        }
                    }
                }
            }
        } else if (e instanceof AttackFinishEvent) {
            active = false;
            mc.moddingAttack = false;
        } else if (active == true) {
            AttackDamageRollEvent adre = (AttackDamageRollEvent) e;
            if (adre.a.attacker == player) {
                if (active) {
                    mc.addDieTo(adre.a.damage);
                }
            }
        }
    }

    @Override
    public double priority() {
        return -1;
    }
}
