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
    public void use(AttackDamageRollEvent adre) {
        mc.addDieTo(adre.a.damage);
        Selectable drop = Query.ask(adre.a.attacker, new SelectQuery("Choose which item to disarm", adre.a.target.wc.getAll(Selectable.class))).response;
        if (!new SavingThrowEvent(adre.a.target, STR, mc.DC.get()).success()) {
            for (int i = 0; i < adre.a.target.wc.hands; i++) {
                if (adre.a.target.wc.held[i] == drop) {
                    adre.a.target.wc.held[i] = null;
                }
            }
        }
    }
}
