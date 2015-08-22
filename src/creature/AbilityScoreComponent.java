package creature;

import amounts.Amount;
import amounts.Value;
import core.AbstractComponent;
import enums.AbilityScore;
import java.util.HashMap;

public class AbilityScoreComponent extends AbstractComponent {

    private HashMap<AbilityScore, Value> abilityScoreMap;

    public AbilityScoreComponent() {
        abilityScoreMap = new HashMap();
        for (AbilityScore as : AbilityScore.values()) {
            abilityScoreMap.put(as, new Value(8));
        }
    }

    public void edit(AbilityScore type, int amt) {
        abilityScoreMap.get(type).flat += amt;
    }

    public void editAll(int[] values) {
        for (int i = 0; i < AbilityScore.values().length; i++) {
            edit(AbilityScore.values()[i], values[i]);
        }
    }

    public Amount get(AbilityScore type) {
        return abilityScoreMap.get(type);
    }

    public int[] getAll() {
        int[] r = new int[AbilityScore.values().length];
        for (int i = 0; i < AbilityScore.values().length; i++) {
            r[i] = get(AbilityScore.values()[i]).get();
        }
        return r;
    }

    public Amount mod(AbilityScore type) {
        return () -> get(type).get() / 2 - 5;
    }

    public void set(AbilityScore type, int amt) {
        abilityScoreMap.get(type).flat = amt;
    }

    public void setAll(int[] values) {
        for (int i = 0; i < AbilityScore.values().length; i++) {
            set(AbilityScore.values()[i], values[i]);
        }
    }
}
