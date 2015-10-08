package maneuvers;

import static actions.Action.Type.BONUS_ACTION;
import static actions.Action.Type.REACTION;
import actions.AttackAction;
import creature.Creature;
import events.UseActionEvent;
import events.attack.AttackDamageRollEvent;
import grid.Square;
import player.Player;
import queries.BooleanQuery;
import queries.Query;
import queries.SquareQuery;

public class Commander___s_Strike extends Maneuver {

    public Creature ally;

    public Commander___s_Strike(Player player, ManeuversComponent mc) {
        super(player, mc);

        add(UseActionEvent.class, (uae) -> {
            if (uae.action.creature == player) {
                if (uae.action instanceof AttackAction) {
                    if (mc.diceUsed < mc.diceCap) {
                        if (player.amc.hasType(BONUS_ACTION)) {
                            if (Query.ask(player, new BooleanQuery("Use the Commander's Strike maneuver?")).response) {
                                mc.diceUsed++;
                                ((AttackAction) uae.action).attacks--;
                                player.amc.useType(BONUS_ACTION, this);
                                Square toHelp = Query.ask(player, new SquareQuery("Choose a creature to give an attack to", (Square) null, -1, true)).response;
                                if (toHelp != null) {
                                    ally = toHelp.creature;
                                    if (ally != null && ally != player) {
                                        if (ally.amc.hasType(REACTION)) {
                                            if (Query.ask(ally, new BooleanQuery("Make a free attack?")).response) {
                                                ally.amc.useType(REACTION, this);
                                                ally.amc.getAction(AttackAction.class).singleAttack();
                                            }
                                        }
                                    }
                                    ally = null;
                                }
                            }
                        }
                    }
                }
            }
        });
        add(AttackDamageRollEvent.class, (adre) -> {
            if (adre.a.attacker == ally) {
                mc.addDieTo(adre.a.damage);
                ally = null;
            }
        });
    }
}
