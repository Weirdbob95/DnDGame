package gui;

import core.AbstractComponent;
import grid.Square;
import java.util.ArrayList;

public class PlayerUIComponent extends AbstractComponent {

    public UIItem selected;
    public boolean mouseOverUI;
    public ArrayList<Square> coloredTiles = new ArrayList();
}
