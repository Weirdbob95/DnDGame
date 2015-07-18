package actions;

import animations.MoveAnimation;
import core.Main;
import creature.Creature;
import grid.GridComponent;
import grid.GridLocationComponent;
import grid.Tile;
import grid.World;
import gui.UIAction;
import gui.UIText;
import gui.parameters.UIChooseSquare;

public class MoveAction extends Action {

    public UIChooseSquare square;

    public MoveAction(Creature creature) {
        super(creature);
    }

    @Override
    protected void act() {
        creature.spc.speedUsed += square.square.distanceTo(Tile.tileAt(creature.getComponent(GridLocationComponent.class).pos));
        new MoveAnimation(creature, square.square.x, square.square.y).start();
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

    @Override
    public void setUIParameters(UIAction uia) {
        new UIText(uia, "Movement remaining: " + (creature.spc.landSpeed - creature.spc.speedUsed) + " ft.", "Small");
        if (square == null) {
            square = new UIChooseSquare(uia);
            GridComponent gc = Main.gameManager.elc.getEntity(World.class).getComponent(GridComponent.class);
            for (int i = 0; i < gc.width; i++) {
                for (int j = 0; j < gc.height; j++) {
                    if (gc.tileGrid[i][j].distanceTo(Tile.tileAt(creature.getComponent(GridLocationComponent.class).pos)) <= creature.spc.landSpeed - creature.spc.speedUsed) {
                        square.options.add(gc.tileGrid[i][j]);
                    }
                }
            }
        } else {
            square.square = null;
            uia.children.add(square);
        }
        square.selected = true;
    }

    @Override
    public boolean validUIParameters() {
        return square.square != null && square.options.contains(square.square);
    }
}
