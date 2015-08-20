package classes;

import enums.AbilityScore;
import enums.CastingType;
import java.io.Serializable;
import player.Player;

public abstract class PlayerClass implements Serializable {

    public Player player;
    public int level;

    public PlayerClass(Player player) {
        this.player = player;
    }

    public CastingType getCastingType() {
        return CastingType.NONE;
    }

    public abstract int hitDie();

    public void levelTo(int newLevel) {
        for (int i = level + 1; i <= newLevel; i++) {
            level = i;
            if (player.clc.level() == 1) {
                player.hc.maxHealth.set("Level 1 Hit Die", hitDie());
            } else {
                player.hc.maxHealth.set("Level " + player.clc.level() + " Hit Die", hitDie() / 2 + 1);
            }
            player.hc.maxHealth.set("Level " + player.clc.level() + " Constitution", player.asc.mod(AbilityScore.CON));
            levelUp(level);
        }
    }

    public abstract void levelUp(int newLevel);
}
