package events;

import actions.Action;

public class FinishActionEvent extends Event {

    public Action action;

    public FinishActionEvent(Action action) {
        this.action = action;
    }
}
