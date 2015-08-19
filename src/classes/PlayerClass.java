package classes;

import creature.Creature;
import enums.CastingType;
import player.SaveItem;

public abstract class PlayerClass implements SaveItem {

    public Creature creature;
    public int level;

    public PlayerClass(Creature creature) {
        this.creature = creature;
    }

    public CastingType getCastingType() {
        return CastingType.NONE;
    }

    public void levelTo(int newLevel) {
        for (int i = level + 1; i <= newLevel; i++) {
            level = i;
            levelUp(level);
        }
    }

    public abstract void levelUp(int newLevel);
}
