package races;

import player.Player;

public class Human extends Race {

    public Human(Player player) {
        super(player);
    }

    @Override
    public int[] getAbilityScores() {
        return new int[]{1, 1, 1, 1, 1, 1};
    }

    @Override
    public void init() {
        super.init();
        player.lc.chooseLanguage();
    }

    @Override
    public String[] languages() {
        return new String[]{"Common"};
    }
}
