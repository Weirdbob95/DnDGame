package events;

public class Event {

    public void call() {
        if (EventHandler.listenerMap.containsKey(getClass())) {
            EventHandler.listenerMap.get(getClass()).forEach(el -> el.onEvent(this));
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
