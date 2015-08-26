package races;

import enums.Size;
import java.io.Serializable;
import java.util.Arrays;
import player.Player;

public abstract class Race implements Serializable {

    public Player player;

    public void addTo(Player player) {
        this.player = player;
        player.asc.editAll(getAbilityScores());
        player.spc.landSpeed.set(getSpeed());
        player.lc.languages.addAll(Arrays.asList(languages()));
    }

    public abstract int[] getAbilityScores();

    public int getSpeed() {
        return 30;
    }

    public abstract String[] languages();

    public Size size() {
        return Size.MEDIUM;
    }
}
