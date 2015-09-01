package races;

import enums.Size;
import events.EventListenerContainer;
import java.util.Arrays;
import player.Player;

public abstract class Race extends EventListenerContainer {

    public Player player;

    public Race(Player player) {
        super(player);
        this.player = player;
    }

    public abstract int[] getAbilityScores();

    public int getSpeed() {
        return 30;
    }

    public void init() {
        player.asc.editAll(getAbilityScores());
        player.spc.landSpeed.set(getSpeed());
        player.lc.languages.addAll(Arrays.asList(languages()));
        player.cdc.size = size();
    }

    public abstract String[] languages();

    public Size size() {
        return Size.MEDIUM;
    }
}
