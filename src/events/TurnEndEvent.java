package events;

import creature.Creature;

public class TurnEndEvent extends Event {

    public Creature creature;

    public TurnEndEvent(Creature creature) {
        this.creature = creature;
    }
}
