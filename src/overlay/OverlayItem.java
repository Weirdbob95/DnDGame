package overlay;

import core.Main;
import ui.PlayerUIComponent;

public abstract class OverlayItem {

    public OverlayItem() {
        Main.gameManager.getComponent(PlayerUIComponent.class).overlayItems.add(this);
    }

    public void destroy() {
        Main.gameManager.getComponent(PlayerUIComponent.class).overlayItems.remove(this);
    }

    public abstract void draw();

    public void onClick() {
    }

    public void onMouseOver() {
    }

    public abstract boolean selected();
}
