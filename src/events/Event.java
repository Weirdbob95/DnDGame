package events;

import util.Log;

public class Event {

    public void call() {
        Log.print(this);
        if (EventHandler.listenerMap.containsKey(getClass())) {
            EventHandler.listenerMap.get(getClass()).stream().forEach(el -> el.onEvent(this));
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
