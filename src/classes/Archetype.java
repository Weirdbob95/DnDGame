package classes;

import java.io.Serializable;
import player.Player;

public abstract class Archetype<C extends PlayerClass> implements Serializable {

    public C playerClass;

    public Archetype(C playerClass) {
        this.playerClass = playerClass;
    }

    public int level() {
        return playerClass.level;
    }

    public abstract void levelUp(int newLevel);

    public Player player() {
        return playerClass.player;
    }
}
