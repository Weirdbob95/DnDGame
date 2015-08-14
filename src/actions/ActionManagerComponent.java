package actions;

import actions.Action.Type;
import static actions.Action.Type.*;
import core.AbstractComponent;
import creature.Creature;
import events.Event;
import events.EventHandler;
import events.EventListener;
import events.TurnStartEvent;
import java.util.ArrayList;

public class ActionManagerComponent extends AbstractComponent implements EventListener {

    private Creature creature;
    public ArrayList<Type> available;
    public ArrayList<Action> actions;

    public ActionManagerComponent(Creature creature) {
        this.creature = creature;
        EventHandler.addListener(this);

        available = new ArrayList();
        available.add(ACTION);
        available.add(BONUS_ACTION);
        available.add(REACTION);
        actions = new ArrayList();

//        actions.add(new AttackAction(creature));
        actions.add(new Dodge(creature));
        actions.add(new MoveAction(creature));
    }

    public ArrayList<Action> allowedActions() {
        ArrayList<Action> r = new ArrayList();
        for (Action a : actions) {
            if (a.isAvaliable()) {
                if (a.getType() == null || available.contains(a.getType())) {
                    r.add(a);
                }
            }
        }
        return r;
    }

    @Override
    public Class<? extends Event>[] callOn() {
        return new Class[]{TurnStartEvent.class};
    }

    public Action getAction(Class<? extends Action> c) {
        for (Action a : actions) {
            if (c.isInstance(a)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public void onEvent(Event e) {
        if (((TurnStartEvent) e).creature == creature) {
            available.clear();
            available.add(ACTION);
            available.add(BONUS_ACTION);
            available.add(REACTION);
        }
    }

    @Override
    public double priority() {
        return 0;
    }
}
