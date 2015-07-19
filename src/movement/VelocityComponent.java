package movement;

import core.AbstractComponent;
import util.Vec2;

public class VelocityComponent extends AbstractComponent {

    public Vec2 vel;

    public VelocityComponent(Vec2 vel) {
        this.vel = vel;
    }

    public VelocityComponent() {
        vel = new Vec2();
    }
}
