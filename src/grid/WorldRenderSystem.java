package grid;

import core.AbstractSystem;
import static org.lwjgl.opengl.GL11.glCallList;

public class WorldRenderSystem extends AbstractSystem {

    private GridComponent gc;

    public WorldRenderSystem(GridComponent lc) {
        this.gc = lc;
    }

    @Override
    public int getLayer() {
        return 0;
    }

    @Override
    public void update() {
        glCallList(gc.list);
    }
}
