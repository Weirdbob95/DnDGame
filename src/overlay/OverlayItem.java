package overlay;

import core.Core;
import ui.PlayerUIComponent;

public abstract class OverlayItem {

    public OverlayItem() {
        Core.gameManager.getComponent(PlayerUIComponent.class).overlayItems.add(this);
    }

    public void destroy() {
        Core.gameManager.getComponent(PlayerUIComponent.class).overlayItems.remove(this);
    }

    public abstract void draw();

    public void onClick() {
    }

    public void onMouseOver() {
    }

    public abstract boolean selected();
}
