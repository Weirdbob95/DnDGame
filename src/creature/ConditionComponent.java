package creature;

import conditions.Condition;
import core.AbstractComponent;
import java.util.HashMap;

public class ConditionComponent extends AbstractComponent {

    public Creature creature;
    public HashMap<Class<? extends Condition>, HashMap<Object, Condition>> conditionMap;

    public ConditionComponent(Creature creature) {
        this.creature = creature;
        conditionMap = new HashMap();
    }

    public HashMap<Object, Condition> getConditions(Class<? extends Condition> c) {
        conditionMap.putIfAbsent(c, new HashMap());
        return conditionMap.get(c);
    }

    public boolean hasAny(Class<? extends Condition>... ca) {
        for (Class c : ca) {
            if (conditionMap.containsKey(c)) {
                return true;
            }
        }
        return false;
    }

    public void remove(Class<? extends Condition> c, Object o) {
        conditionMap.get(c).get(o).remove();
    }
}
