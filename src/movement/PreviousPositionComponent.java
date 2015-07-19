package movement;

import core.AbstractComponent;
import util.Vec2;

public class PreviousPositionComponent extends AbstractComponent {

    public Vec2 pos;

    public PreviousPositionComponent(Vec2 pos) {
        this.pos = pos;
    }
}
