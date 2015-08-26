package maneuvers;

import static actions.Action.Type.BONUS_ACTION;
import static actions.Action.Type.REACTION;
import actions.AttackAction;
import creature.Creature;
import events.Event;
import events.UseActionEvent;
import events.attack.AttackDamageRollEvent;
import grid.Square;
import player.Player;
import queries.BooleanQuery;
import queries.Query;
import queries.SquareQuery;

public class Commander__s_Strike extends Maneuver {

    public Creature ally;

    public Commander__s_Strike(Player player, ManeuversComponent mc) {
        super(player, mc);
    }

    @Override
    public Class<? extends Event>[] callOn() {
        return new Class[]{UseActionEvent.class, AttackDamageRollEvent.class};
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof UseActionEvent) {
            UseActionEvent uae = (UseActionEvent) e;
            if (uae.action.creature == player) {
                if (uae.action instanceof AttackAction) {
                    if (mc.diceUsed < mc.diceCap) {
                        if (player.amc.available.contains(BONUS_ACTION)) {
                            if (Query.ask(player, new BooleanQuery("Use the Commander's Strike maneuver?")).response) {
                                mc.diceUsed++;
                                ((AttackAction) uae.action).attacks--;
                                player.amc.available.remove(BONUS_ACTION);
                                Square toHelp = Query.ask(player, new SquareQuery("Choose a creature to give an attack to", (Square) null, -1, true)).response;
                                if (toHelp != null) {
                                    ally = toHelp.creature;
                                    if (ally != null && ally != player) {
                                        if (ally.amc.available.contains(REACTION)) {
                                            if (Query.ask(ally, new BooleanQuery("Make a free attack?")).response) {
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
        } else {
            AttackDamageRollEvent adre = (AttackDamageRollEvent) e;
            if (adre.a.attacker == ally) {
                mc.addDieTo(adre.a.damage);
                ally = null;
            }
        }
    }
}
