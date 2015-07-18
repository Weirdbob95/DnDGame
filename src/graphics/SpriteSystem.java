package graphics;

import core.AbstractSystem;
import grid.GridLocationComponent;

public class SpriteSystem extends AbstractSystem {

    private GridLocationComponent glc;
    private SpriteComponent sc;

    public SpriteSystem(GridLocationComponent glc, SpriteComponent sc) {
        this.glc = glc;
        this.sc = sc;
    }

    @Override
    public void update() {
        if (sc.visible) {
            Graphics2D.drawSprite(sc.getTexture(), glc.pos, sc.scale, glc.rot, sc.color);
        }
        sc.imageIndex += sc.imageSpeed;
    }

}
