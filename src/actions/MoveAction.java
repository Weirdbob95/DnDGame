package actions;

import creature.Creature;
import queries.PathQuery;
import queries.Query;

public class MoveAction extends Action {

    public MoveAction(Creature creature) {
        super(creature);
    }

    @Override
    protected void act() {
        PathQuery path = Query.ask(creature, new PathQuery("Choose which square to move to",
                creature.glc.lowerLeft, (int) Math.round(creature.spc.landSpeed.get() * creature.spc.speedPercRemaining), creature.cdc.size.squares, false, creature));
        if (!path.path.isEmpty()) {
            creature.spc.speedPercRemaining -= (double) path.distance() / creature.spc.landSpeed.get();
            creature.glc.moveAlongPath(path.path);
        }
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
