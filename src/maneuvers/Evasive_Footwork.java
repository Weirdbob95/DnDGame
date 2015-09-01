package maneuvers;

import amounts.Die;
import events.move.PathMoveEvent;
import events.move.PathMoveFinishEvent;
import player.Player;
import queries.BooleanQuery;
import queries.Query;

public class Evasive_Footwork extends Maneuver {

    public Evasive_Footwork(Player player, ManeuversComponent mc) {
        super(player, mc);

        add(PathMoveEvent.class, e -> {
            if (e.creature == player) {
                if (mc.diceUsed < mc.diceCap) {
                    if (Query.ask(player, new BooleanQuery("Use the Evasive Footwork maneuver?")).response) {
                        player.ac.AC.set("Evasive Footwork", new Die(mc.dieSize));
                        mc.diceUsed++;
                    }
                }
            }
        });
        add(PathMoveFinishEvent.class, e -> {
            if (e.pme.creature == player) {
                player.ac.AC.components.remove("Evasive Footwork");
            }
        });
    }
}
