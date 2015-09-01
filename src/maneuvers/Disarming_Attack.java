package maneuvers;

import static enums.AbilityScore.STR;
import events.SavingThrowEvent;
import events.attack.AttackDamageRollEvent;
import player.Player;
import queries.Query;
import queries.SelectQuery;
import util.Selectable;

public class Disarming_Attack extends AttackManeuver {

    public Disarming_Attack(Player player, ManeuversComponent mc) {
        super(player, mc);
    }

    @Override
    public void use(AttackDamageRollEvent e) {
        mc.addDieTo(e.a.damage);
        Selectable drop = Query.ask(e.a.attacker, new SelectQuery("Choose which item to disarm", e.a.target.wc.getAll(Selectable.class))).response;
        if (SavingThrowEvent.fail(e.a.target, STR, mc.DC.get())) {
            for (int i = 0; i < e.a.target.wc.hands; i++) {
                if (e.a.target.wc.held[i] == drop) {
                    e.a.target.wc.held[i] = null;
                }
            }
        }
    }
}
