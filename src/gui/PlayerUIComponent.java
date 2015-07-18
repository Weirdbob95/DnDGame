package gui;

import core.AbstractComponent;
import grid.Tile;
import java.util.ArrayList;

public class PlayerUIComponent extends AbstractComponent {

    public UIItem selected;
    public boolean mouseOverUI;
    public ArrayList<Tile> coloredTiles = new ArrayList();
}
