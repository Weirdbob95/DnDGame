package animations;

import core.Core;
import util.Vec2;

public class ViewMoveAnimation extends Animation {

    public Vec2 goal;

    public ViewMoveAnimation(Vec2 goal) {
        this.goal = goal;
    }

    @Override
    public void update() {
        Vec2 diff = goal.subtract(Core.gameManager.rmc.viewPos);
        if (diff.lengthSquared() < 1) {
            Core.gameManager.rmc.viewPos = goal;
            finish();
        } else if (diff.lengthSquared() < 10) {
            Core.gameManager.rmc.viewPos = Core.gameManager.rmc.viewPos.add(diff.setLength(1));
        } else {
            Core.gameManager.rmc.viewPos = Core.gameManager.rmc.viewPos.add(diff.multiply(.1));
        }
    }
}
