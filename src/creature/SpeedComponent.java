package creature;

import core.AbstractComponent;
import events.Event;
import events.EventHandler;
import events.EventListener;
import events.TurnStartEvent;

public class SpeedComponent extends AbstractComponent implements EventListener {

    public int landSpeed;
    public int climbSpeed;
    public int flySpeed;
    public int burrowSpeed;
    public int speedUsed;

    public SpeedComponent() {
        EventHandler.addListener(this);
    }

    @Override
    public Class<? extends Event>[] callOn() {
        return new Class[]{TurnStartEvent.class};
    }

    @Override
    public void onEvent(Event e) {
        speedUsed = 0;
    }

    @Override
    public double priority() {
        return 0;
    }
}
