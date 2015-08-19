package races;

import creature.Creature;
import player.SaveItem;

public abstract class Race implements SaveItem {

    public Creature creature;

    public Race(Creature creature) {
        this.creature = creature;
    }
}
