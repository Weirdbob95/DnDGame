package events;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public abstract class EventHandler {

    public static HashMap<Class<? extends Event>, PriorityQueue<EventListener>> listenerMap = new HashMap();

    public static void addListener(EventListener el) {
        for (Class<? extends Event> c : el.callOn()) {
            if (!listenerMap.containsKey(c)) {
                listenerMap.put(c, new PriorityQueue(Comparator.comparingDouble(EventListener::priority)));
            }
            listenerMap.get(c).add(el);
        }
    }

    public static void removeListener(EventListener el) {
        for (Class<? extends Event> c : el.callOn()) {
            listenerMap.get(c).remove(el);
            if (listenerMap.get(c).isEmpty()) {
                listenerMap.remove(c);
            }
        }
    }
}
