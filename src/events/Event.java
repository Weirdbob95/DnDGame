package events;

import util.Log;

public class Event {

    public void call() {
        Log.print(this);
        if (EventHandler.listenerMap.containsKey(getClass())) {
            for (EventListener el : EventHandler.listenerMap.get(getClass())) {
                el.onEvent(this);
            }
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
