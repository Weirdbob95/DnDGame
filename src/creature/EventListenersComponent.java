package creature;

import core.AbstractComponent;
import events.Event;
import events.EventListener;
import java.util.HashMap;

public class EventListenersComponent extends AbstractComponent {

    public HashMap<EventListener, Class<? extends Event>> listenerMap = new HashMap();
}
