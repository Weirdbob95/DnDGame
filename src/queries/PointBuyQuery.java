package queries;

import enums.AbilityScore;
import java.util.Arrays;
import ui.UIChooseButton;
import ui.UIPointBuy;
import ui.UIText;

public class PointBuyQuery extends Query {

    public static final int ABILITY_SCORE_COUNT = AbilityScore.values().length;

    public final int points;
    public int[] abilityScores;
    public int[] maximums;
    public UIText uiPoints;
    public UIPointBuy[] uiList;
    public int[] response;

    public PointBuyQuery(int points, int[] abilityScores, int[] maximums) {
        this.points = points;
        this.abilityScores = abilityScores;
        this.maximums = maximums;
        response = Arrays.copyOf(abilityScores, abilityScores.length);
    }

    public static int cost(int amount) {
        switch (amount) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 7;
            case 7:
                return 9;
            default:
                return Integer.MAX_VALUE;
        }
    }

    private static int costToChange(int prev, int change) {
        return cost(prev + change) - cost(prev);
    }

    @Override
    public boolean createUI() {
        if (points == 0) {
            return false;
        }
        new UIText(puic.root, "Spend points to improve your ability scores");
        uiPoints = new UIText(puic.root, "");
        uiList = new UIPointBuy[ABILITY_SCORE_COUNT];
        for (int i = 0; i < ABILITY_SCORE_COUNT; i++) {
            uiList[i] = new UIPointBuy(puic.root, this);
        }
        new UIChooseButton(puic.root, "Finish", this);
        updateUI();
        return true;
    }

    public void updateUI() {
        int remainingPoints = points;
        for (UIPointBuy i : uiList) {
            remainingPoints -= cost(i.value);
        }
        uiPoints.text = "Points remaining: " + remainingPoints;
        for (int pos = 0; pos < ABILITY_SCORE_COUNT; pos++) {
            UIPointBuy i = uiList[pos];
            response[pos] = abilityScores[pos] + i.value;
            i.text = AbilityScore.values()[pos].longName() + ": " + response[pos];
            i.minus.disabled = costToChange(i.value, -1) > remainingPoints;
            i.plus.disabled = costToChange(i.value, 1) > remainingPoints || response[pos] + 1 > maximums[pos];
        }
    }
}
