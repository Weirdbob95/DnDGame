package movement;

import core.AbstractComponent;
import util.Vec2;

public class GravityComponent extends AbstractComponent {

    public Vec2 g;

    public GravityComponent() {
        g = new Vec2(0, -.6);
    }
}
