package creature;

import core.AbstractComponent;
import events.EventListener;
import java.util.ArrayList;

public class EventListenersComponent extends AbstractComponent {

    public ArrayList<EventListener> listenerList = new ArrayList();
}
