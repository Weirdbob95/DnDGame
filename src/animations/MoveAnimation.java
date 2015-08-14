package animations;

import creature.Creature;
import movement.PositionComponent;
import movement.RotationComponent;
import util.Vec2;

public class MoveAnimation extends Animation {

    public PositionComponent pc;
    public Vec2 goal;
    public Vec2 speed;

    public MoveAnimation(Creature c, Vec2 goal) {
        pc = c.getComponent(PositionComponent.class);
        this.goal = goal;
        speed = goal.subtract(pc.pos).setLength(4);
        c.getComponent(RotationComponent.class).rot = speed.direction();
    }

    @Override
    public void update() {
        if (goal.subtract(pc.pos).lengthSquared() < speed.lengthSquared()) {
            pc.pos = goal;
            finish();
        } else {
            pc.pos = pc.pos.add(speed);
        }
    }
}
