package gui.parameters;

import core.Main;
import core.MouseInput;
import grid.Square;
import grid.World;
import gui.PlayerUIComponent;
import gui.UIAction;
import java.util.ArrayList;

public class UIChooseSquare extends UIParameter {

    public Square square;
    public ArrayList<Square> options;

    public UIChooseSquare(UIAction parent) {
        super(parent, "Choose a square");
        options = new ArrayList();
    }

    @Override
    public void choose() {
        square = World.grid.tileAt(MouseInput.mouse());
    }

    @Override
    public void draw() {
        if (selected) {
            Square t = World.grid.tileAt(MouseInput.mouse());
            if (t != null) {
                Main.gameManager.getComponent(PlayerUIComponent.class).coloredTiles.add(t);
            }
        } else if (square != null) {
            Main.gameManager.getComponent(PlayerUIComponent.class).coloredTiles.add(square);
        }
        super.draw();
    }

}
