package events;

import amounts.Die;
import creature.Creature;

public class DieRollEvent extends Event {

    public Creature creature;
    public Die die;

    public DieRollEvent(Creature creature, Die die) {
        this.creature = creature;
        this.die = die;
        call();
    }

}
