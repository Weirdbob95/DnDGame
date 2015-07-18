package actions;

import core.Main;
import creature.Creature;
import grid.World;
import rounds.InitiativeOrderComponent;

public class EndTurnAction extends Action {

    public EndTurnAction(Creature creature) {
        super(creature);
    }

    @Override
    protected void act() {
        Main.gameManager.elc.getEntity(World.class).getComponent(InitiativeOrderComponent.class).endTurn();
    }

    @Override
    public String[] defaultTabs() {
        return new String[]{""};
    }

    @Override
    public String description() {
        return "Ends your turn. You take no further actions.";
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public String toString() {
        return "End Turn";
    }
}
