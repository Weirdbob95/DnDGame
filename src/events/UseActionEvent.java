package events;

import actions.Action;
import util.Log;

public class UseActionEvent extends Event {

    public Action action;

    public UseActionEvent(Action action) {
        this.action = action;
    }

    @Override
    public void call() {
        Log.print("Used action: " + action.getName());
        super.call();
    }
}
