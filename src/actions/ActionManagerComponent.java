package actions;

import actions.Action.Type;
import static actions.Action.Type.*;
import core.AbstractComponent;
import creature.Creature;
import events.EventListener;
import events.TurnStartEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class ActionManagerComponent extends AbstractComponent {

    private Creature creature;
    private ArrayList<Action> actions;
    public ArrayList<Type> available;

    public ActionManagerComponent(Creature creature) {
        this.creature = creature;

        available = new ArrayList();
        available.add(ACTION);
        available.add(BONUS_ACTION);
        available.add(REACTION);
        actions = new ArrayList();

        actions.add(new AttackAction(creature));
        actions.add(new Dodge(creature));
        actions.add(new MoveAction(creature));
        actions.add(new Dash(creature));

        EventListener.createListener(creature, TurnStartEvent.class, e -> {
            if (e.creature == creature) {
                available.clear();
                available.add(ACTION);
                available.add(BONUS_ACTION);
                available.add(REACTION);
            }
        });
    }

    public void addAction(Action a) {
        actions.add(a);
        a.add();
    }

    public ArrayList<Action> allowedActions() {
        return new ArrayList(Arrays.asList(actions.stream().filter(Action::isAvailable).filter(a -> a.getType() == null || available.contains(a.getType())).toArray(l -> new Action[l])));
    }

    public <A extends Action> A getAction(Class<A> c) {
        return actions.stream().filter(a -> c.isInstance(a)).map(a -> (A) a).findFirst().orElse(null);
    }

    public void removeAction(Class<? extends Action> c) {
        Action a = getAction(c);
        if (a != null) {
            actions.remove(a);
            a.remove();
        }
    }
}
