package rounds;

import animations.Animation;
import core.AbstractSystem;

public class RoundSystem extends AbstractSystem {

    private InitiativeOrderComponent ioc;
    public Animation anim;

    public RoundSystem(InitiativeOrderComponent ioc) {
        this.ioc = ioc;
    }

    @Override
    public void update() {
        if (anim == null) {
            if (ioc.current().controller.go()) {
                ioc.endTurn();
            }
        } else {
            if (anim.update()) {
                anim = null;
            }
        }
    }

}
