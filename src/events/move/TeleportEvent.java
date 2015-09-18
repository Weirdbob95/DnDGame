package events.move;

import animations.MoveAnimation;
import creature.Creature;
import events.Event;
import grid.GridUtils;
import grid.Square;
import java.util.List;
import java.util.stream.Collectors;
import queries.Query;
import queries.SquareQuery;
import util.Vec2;

public class TeleportEvent extends Event {

    public Creature creature;
    public Square from;
    public Square to;
    public boolean animate;

    public TeleportEvent(Creature creature, Square to, boolean animate) {
        this.creature = creature;
        this.to = to;
        this.animate = animate;
    }

    @Override
    public void call() {
        super.call();
        creature.glc.moveToSquare(to);
        if (animate) {
            new MoveAnimation(creature, creature.glc.center()).start();
        } else {
            creature.glc.updateSpritePos();
        }
        if (PathMoveEvent.current != null) {
            if (PathMoveEvent.current.creature == creature) {
                PathMoveEvent.current.stopped = true;
            }
        }
    }

    public static void pushAway(Creature pusher, Creature target, int distance) {
        List<Square> options = GridUtils.all().stream().filter(s -> {
            if (s == target.glc.lowerLeft) {
                return true;
            }
            if (GridUtils.distance(s, target.glc.lowerLeft) > distance) {
                return false;
            }
            Vec2 youToCreature = target.glc.center().subtract(pusher.glc.center());
            Vec2 creatureToPos = target.glc.centerAt(s).subtract(target.glc.center());
            return Math.PI / 4 > Math.acos(youToCreature.dot(creatureToPos) / youToCreature.length() / creatureToPos.length());
        }).collect(Collectors.toList());
        new TeleportEvent(target, Query.ask(pusher, new SquareQuery("Choose where to push your target to", options)).response, true).call();
    }
}
