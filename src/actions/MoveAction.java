package actions;

import creature.Creature;
import events.move.PathMoveEvent;
import grid.GridUtils;

public class MoveAction extends Action {

    public MoveAction(Creature creature) {
        super(creature);
    }

    @Override
    protected void act() {
        PathMoveEvent pme = new PathMoveEvent(creature, (int) Math.round(creature.spc.landSpeed.get() * creature.spc.speedPercRemaining));
        pme.call();
        creature.spc.speedPercRemaining -= (double) GridUtils.distance(creature.glc.lowerLeft, pme.partialPath) / creature.spc.landSpeed.get();
    }

    @Override
    public String[] defaultTabs() {
        return new String[]{""};
    }

    @Override
    public String getDescription() {
        return "Move to a square.";
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public boolean isAvailable() {
        return creature.spc.speedPercRemaining > 0;
    }
}
