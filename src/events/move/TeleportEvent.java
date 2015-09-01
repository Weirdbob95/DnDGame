package events.move;

import animations.MoveAnimation;
import creature.Creature;
import events.Event;
import grid.Square;

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
}
