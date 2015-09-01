package events.move;

import animations.MoveAnimation;
import creature.Creature;
import events.Event;
import grid.Square;
import java.util.ArrayList;
import queries.PathQuery;
import queries.Query;

public class PathMoveEvent extends Event {

    public static PathMoveEvent current;

    public Creature creature;
    public ArrayList<Square> path;
    public ArrayList<Square> partialPath;
    public Square start;
    public boolean stopped;

    public PathMoveEvent(Creature creature, ArrayList<Square> path) {
        this.creature = creature;
        this.path = path;
        partialPath = new ArrayList();
        start = creature.glc.lowerLeft;
    }

    public PathMoveEvent(Creature creature, int maxDist) {
        this.creature = creature;
        path = Query.ask(creature, new PathQuery("Choose a path to move along", creature.glc.lowerLeft, maxDist,
                creature.cdc.size.squares, (a, b) -> {
                    SegmentCheckEvent e = new SegmentCheckEvent(creature, a, b, false);
                    e.call();
                    return e.allow;
                })).path;
        partialPath = new ArrayList();
        start = creature.glc.lowerLeft;
    }

    @Override
    public void call() {
        current = this;
        super.call();
        if (!stopped) {
            for (int i = 0; i < path.size(); i++) {
                Square prev = (i == 0 ? start : path.get(i - 1));
                Square next = path.get(i);

                new PreSegmentMoveEvent(this, prev, next).call();
                if (stopped) {
                    break;
                }

                partialPath.add(next);
                creature.glc.moveToSquare(next);
                new MoveAnimation(creature, creature.glc.center()).start();

                new PostSegmentMoveEvent(this, prev, next).call();
                if (stopped) {
                    break;
                }
            }
        }
        new PathMoveFinishEvent(this).call();
        current = null;
    }
}
