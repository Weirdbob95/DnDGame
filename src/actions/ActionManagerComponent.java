package actions;

import actions.Action.Type;
import static actions.Action.Type.*;
import core.AbstractComponent;
import creature.Creature;
import events.EventListener;
import events.HasActionTypeEvent;
import events.TurnStartEvent;
import events.UseActionTypeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActionManagerComponent extends AbstractComponent {

    private Creature creature;
    private ArrayList<Action> actions;
    private ArrayList<Type> available;

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

    public <A extends Action> A addAction(A a) {
        actions.add(a);
        a.add();
        return a;
    }

    public void addType(Type type) {
        available.add(type);
    }

    public List<Action> allowedActions() {
        return actions.stream().filter(Action::isAvailable).filter(a -> a.getType() == null || available.contains(a.getType())).collect(Collectors.toList());
    }

    public <A extends Action> A getAction(Class<A> c) {
        return actions.stream().filter(c::isInstance).map(a -> (A) a).findFirst().orElse(null);
    }

    public <A extends Action> List<A> getActionList(Class<A> c) {
        return actions.stream().filter(c::isInstance).map(a -> (A) a).collect(Collectors.toList());
    }

    public boolean hasType(Type type) {
        HasActionTypeEvent e = new HasActionTypeEvent(creature, type, available.contains(type));
        e.call();
        return e.available;
    }

    public void removeAction(Class<? extends Action> c) {
        Action a = getAction(c);
        if (a != null) {
            actions.remove(a);
            a.remove();
        }
    }

    public void useType(Type type, Object purpose) {
        available.remove(type);
        new UseActionTypeEvent(creature, type, purpose).call();
    }
}
