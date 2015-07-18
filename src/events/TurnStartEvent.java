package events;

import animations.ViewMoveAnimation;
import creature.Creature;
import grid.GridLocationComponent;

public class TurnStartEvent extends Event {

    public Creature creature;

    public TurnStartEvent(Creature creature) {
        this.creature = creature;
    }

    @Override
    public void call() {
        new ViewMoveAnimation(creature.getComponent(GridLocationComponent.class).pos).start();
        super.call();
    }
}
