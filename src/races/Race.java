package races;

import java.io.Serializable;
import player.Player;

public abstract class Race implements Serializable {

    public Player player;

    public void addTo(Player player) {
        this.player = player;
        player.asc.editAll(getAbilityScores());
        player.spc.landSpeed.set(getSpeed());
    }

    public abstract int[] getAbilityScores();

    public int getSpeed() {
        return 30;
    }
}
