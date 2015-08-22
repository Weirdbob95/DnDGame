package races;

import player.Player;

public class Human extends Race {

    @Override
    public void addTo(Player player) {
        super.addTo(player);
        player.lc.chooseLanguage();
    }

    @Override
    public int[] getAbilityScores() {
        return new int[]{1, 1, 1, 1, 1, 1};
    }

    @Override
    public String[] languages() {
        return new String[]{"Common"};
    }
}
