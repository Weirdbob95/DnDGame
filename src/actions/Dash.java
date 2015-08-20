package actions;

import static actions.Action.Type.ACTION;
import creature.Creature;

public class Dash extends Action {

    public Dash(Creature creature) {
        super(creature);
    }

    @Override
    protected void act() {
        creature.spc.speedPercRemaining += 1;
    }

    @Override
    public String[] defaultTabs() {
        return new String[]{};
    }

    @Override
    public String getDescription() {
        return "You gain extra movement for the current turn.";
    }

    @Override
    public Type getType() {
        return ACTION;
    }
}
