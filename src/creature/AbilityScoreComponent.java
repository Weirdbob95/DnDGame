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
            abilityScoreMap.put(as, 10);
        }
    }

    public Value get(AbilityScore type) {
        return new Value(abilityScoreMap.get(type));
    }

    public Value mod(AbilityScore type) {
        return new Value(abilityScoreMap.get(type) / 2 - 5);
    }

    public void set(AbilityScore type, int amt) {
        abilityScoreMap.put(type, amt);
    }
}
