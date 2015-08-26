package events;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public abstract class EventHandler {

    public static HashMap<Class<? extends Event>, PriorityQueue<EventListener>> listenerMap = new HashMap();

    public static void addListener(EventListener el, Class<? extends Event> c) {
        if (!listenerMap.containsKey(c)) {
            listenerMap.put(c, new PriorityQueue<>(Comparator.comparingDouble(e -> e.priority())));
        }
        listenerMap.get(c).add(el);
    }

    public static void removeListener(EventListener el, Class<? extends Event> c) {
        listenerMap.get(c).remove(el);
        if (listenerMap.get(c).isEmpty()) {
            listenerMap.remove(c);
        }
    }
}
