package events;

import actions.Action;
import creature.Creature;
import java.util.ArrayList;

public class GetActionsEvent extends Event {

    public Creature creature;
    public ArrayList<Action> actionList;

    public GetActionsEvent(Creature creature) {
        this.creature = creature;
        actionList = new ArrayList();
    }
}
