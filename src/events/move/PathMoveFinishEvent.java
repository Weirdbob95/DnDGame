package events.move;

import events.Event;

public class PathMoveFinishEvent extends Event {

    public PathMoveEvent pme;

    public PathMoveFinishEvent(PathMoveEvent pme) {
        this.pme = pme;
    }
}
