package events.move;

import events.Event;
import grid.Square;

public class PostSegmentMoveEvent extends Event {

    public PathMoveEvent pme;
    public Square from;
    public Square to;

    public PostSegmentMoveEvent(PathMoveEvent pme, Square from, Square to) {
        this.pme = pme;
        this.from = from;
        this.to = to;
    }
}
