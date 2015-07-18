package events;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import util.Util;

public abstract class EventHandler {

    public static HashMap<Class<? extends Event>, PriorityQueue<EventListener>> listenerMap = new HashMap();

    public static void addListener(EventListener el) {
        for (Class<? extends Event> c : el.callOn()) {
            if (!listenerMap.containsKey(c)) {
                listenerMap.put(c, new PriorityQueue(1, new Comparator() {
                    @Override
                    public int compare(Object t, Object t1) {
                        return Util.sign(((EventListener) t).priority() - ((EventListener) t1).priority());
                    }
                }));
            }
            listenerMap.get(c).add(el);
        }
    }
}
