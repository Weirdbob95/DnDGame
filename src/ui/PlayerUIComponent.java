package ui;

import core.AbstractComponent;
import java.util.ArrayList;
import overlay.OverlayItem;

public class PlayerUIComponent extends AbstractComponent {

    public UIItem root;
    public boolean mouseOverUI;
    public ArrayList<OverlayItem> overlayItems = new ArrayList();
    //public UISquare selectedSquare;
}
