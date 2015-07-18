package animations;

import core.Main;
import util.Vec2;

public class ViewMoveAnimation extends Animation {

    public Vec2 goal;

    public ViewMoveAnimation(Vec2 goal) {
        this.goal = goal;
    }

    @Override
    public boolean update() {
        Vec2 diff = goal.subtract(Main.gameManager.rmc.viewPos);
        if (diff.lengthSquared() < 1) {
            Main.gameManager.rmc.viewPos = goal;
            return true;
        } else if (diff.lengthSquared() < 10) {
            Main.gameManager.rmc.viewPos = Main.gameManager.rmc.viewPos.add(diff.setLength(1));
        } else {
            Main.gameManager.rmc.viewPos = Main.gameManager.rmc.viewPos.add(diff.multiply(.1));
        }
        return false;
    }
}
