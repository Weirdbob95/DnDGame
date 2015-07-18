package creature;

import events.AbstractEventListener;
import events.Event;

public class CreatureListener extends AbstractEventListener {

    private Creature creature;

    public CreatureListener(Creature creature) {
        this.creature = creature;
    }

    @Override
    public Class<? extends Event>[] callOn() {
        return new Class[]{};
    }

    @Override
    public void onEvent(Event e) {
//        if (e.creature == creature) {
//            creature.amc.available.clear();
//            available.add(ACTION);
//            available.add(BONUS_ACTION);
//            available.add(REACTION);
//        }
    }
}
