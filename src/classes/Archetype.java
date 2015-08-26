package classes;

import events.EventListenerContainer;
import player.Player;

public abstract class Archetype<C extends PlayerClass> extends EventListenerContainer {

    public C playerClass;

    public Archetype(C playerClass) {
        super(playerClass.player);
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
