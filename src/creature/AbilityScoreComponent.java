package creature;

import amounts.Value;
import core.AbstractComponent;
import enums.AbilityScore;
import java.util.HashMap;

public class AbilityScoreComponent extends AbstractComponent {

    private HashMap<AbilityScore, Integer> abilityScoreMap;

    public AbilityScoreComponent() {
        abilityScoreMap = new HashMap();
        for (AbilityScore as : AbilityScore.values()) {
            abilityScoreMap.put(as, 8);
        }
    }

    public void edit(AbilityScore type, int amt) {
        set(type, get(type).get() + amt);
    }

    public void editAll(int[] values) {
        for (int i = 0; i < AbilityScore.values().length; i++) {
            edit(AbilityScore.values()[i], values[i]);
        }
    }

    public Value get(AbilityScore type) {
        return new Value(abilityScoreMap.get(type));
    }

    public int[] getAll() {
        int[] r = new int[AbilityScore.values().length];
        for (int i = 0; i < AbilityScore.values().length; i++) {
            r[i] = get(AbilityScore.values()[i]).get();
        }
        return r;
    }

    public Value mod(AbilityScore type) {
        return new Value(abilityScoreMap.get(type) / 2 - 5);
    }

    public void set(AbilityScore type, int amt) {
        abilityScoreMap.put(type, amt);
    }

    public void setAll(int[] values) {
        for (int i = 0; i < AbilityScore.values().length; i++) {
            set(AbilityScore.values()[i], values[i]);
        }
    }
}
