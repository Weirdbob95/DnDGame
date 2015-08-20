package events;

import actions.Action;

public class UseActionEvent extends Event {

    public Action action;

    public UseActionEvent(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Used action: " + action.getName();
    }
}
