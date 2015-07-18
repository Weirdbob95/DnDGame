package events;

public class Event {

    public void call() {
        for (EventListener el : EventHandler.listenerMap.get(getClass())) {
            el.onEvent(this);
        }
    }
}
