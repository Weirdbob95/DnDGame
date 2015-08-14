package graphics;

import core.AbstractSystem;
import core.Main;
import util.Vec2;

public class GUISystem extends AbstractSystem {

    @Override
    public int getLayer() {
        return 3;
    }

    @Override
    public void update() {
        Camera.setProjection2D(new Vec2(), Main.gameManager.rmc.viewSize);
    }
}
