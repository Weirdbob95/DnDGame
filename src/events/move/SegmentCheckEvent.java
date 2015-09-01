package events.move;

import creature.Creature;
import events.Event;
import grid.GridUtils;
import grid.Square;

public class SegmentCheckEvent extends Event {

    public Creature creature;
    public Square from;
    public Square to;
    public boolean teleport;
    public boolean allow;

    public SegmentCheckEvent(Creature creature, Square from, Square to, boolean teleport) {
        this.creature = creature;
        this.from = from;
        this.to = to;
        this.teleport = teleport;
        allow = GridUtils.isOpen(to, creature.cdc.size.squares, creature);
    }
}
