package overlay;

import core.AbstractSystem;
import core.MouseInput;
import java.util.ArrayList;
import ui.PlayerUIComponent;

public class OverlaySystem extends AbstractSystem {

    private PlayerUIComponent puic;

    public OverlaySystem(PlayerUIComponent puic) {
        this.puic = puic;
    }

    @Override
    public int getLayer() {
        return 2;
    }

    @Override
    public void update() {
        for (OverlayItem i : new ArrayList<>(puic.overlayItems)) {
            i.draw();
            if (i.selected()) {
                i.onMouseOver();
                if (MouseInput.isPressed(0)) {
                    i.onClick();
                }
            }
        }
    }
}
