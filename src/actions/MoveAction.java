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
                creature.glc.lowerLeft, creature.spc.landSpeed - creature.spc.speedUsed, creature.cdc.size.squares, false, creature));
        if (!path.path.isEmpty()) {
            creature.spc.speedUsed += path.distance();
            creature.glc.moveAlongPath(path.path);
        }
    }

    @Override
    public String[] defaultTabs() {
        return new String[]{""};
    }

    @Override
    public String description() {
        return "Move to a square.";
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public boolean isAvaliable() {
        return creature.spc.landSpeed > creature.spc.speedUsed;
    }
}
