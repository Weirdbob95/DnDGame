package maneuvers;

import static enums.AbilityScore.STR;
import static enums.Size.LARGE;
import events.SavingThrowEvent;
import events.attack.AttackDamageRollEvent;
import events.move.TeleportEvent;
import grid.GridUtils;
import grid.Square;
import java.util.List;
import java.util.stream.Collectors;
import player.Player;
import queries.Query;
import queries.SquareQuery;
import util.Vec2;

public class Pushing_Attack extends AttackManeuver {

    public Pushing_Attack(Player player, ManeuversComponent mc) {
        super(player, mc);
    }

    @Override
    public void use(AttackDamageRollEvent e) {
        mc.addDieTo(e.a.damage);
        if (e.a.target.cdc.size.squares <= LARGE.squares) {
            if (SavingThrowEvent.fail(e.a.target, STR, mc.DC.get())) {
                List<Square> options = GridUtils.all().stream().filter(s -> {
                    if (s == e.a.target.glc.lowerLeft) {
                        return true;
                    }
                    if (GridUtils.distance(s, e.a.target.glc.lowerLeft) > 15) {
                        return false;
                    }
                    Vec2 youToCreature = e.a.target.glc.center().subtract(player.glc.center());
                    Vec2 creatureToPos = e.a.target.glc.centerAt(s).subtract(e.a.target.glc.center());
                    return Math.PI / 4 > Math.acos(youToCreature.dot(creatureToPos) / youToCreature.length() / creatureToPos.length());
                }).collect(Collectors.toList());
                new TeleportEvent(e.a.target, Query.ask(player, new SquareQuery("Choose where to push your target to", options)).response, true).call();
                //e.a.target.glc.moveToSquare(Query.ask(player, new SquareQuery("Choose where to push your target to", options)).response);
                //e.a.target.glc.updateSpritePos();
            }
        }
    }
}
