package races;

import creature.Creature;
import java.util.HashMap;

public class Human extends Race {

    public Human(Creature creature) {
        super(creature);
        creature.asc.editAll(new int[]{1, 1, 1, 1, 1, 1});
    }

    @Override
    public void fromText(HashMap<String, String> text) {
    }

    @Override
    public HashMap<String, String> toText() {
        return new HashMap();
    }

}
