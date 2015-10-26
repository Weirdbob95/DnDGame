package maneuvers;

import static actions.Action.Type.REACTION;
import amounts.Die;
import static enums.AbilityScore.DEX;
import events.TakeDamageEvent;
import events.attack.AttackEvent;
import player.Player;
import queries.BooleanQuery;
import queries.Query;

public class Parry extends Maneuver {

    public Parry(Player player, ManeuversComponent mc) {
        super(player, mc);

        add(TakeDamageEvent.class, e -> {
            if (e.target == player) {
                if (e.source instanceof AttackEvent) {
                    if (!((AttackEvent) e.source).isRanged) {
                        if (mc.diceUsed < mc.diceCap) {
                            if (player.amc.hasType(REACTION)) {
                                if (Query.ask(player, new BooleanQuery("Use the Parry maneuver?")).response) {
                                    player.amc.useType(REACTION, this);
                                    e.damage -= new Die(mc.dieSize).roll + player.asc.mod(DEX).get();
                                    mc.diceUsed++;
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
