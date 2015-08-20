package creature;

import amounts.Stat;
import core.AbstractComponent;
import events.Event;
import events.EventListener;
import events.TurnStartEvent;

public class SpeedComponent extends AbstractComponent implements EventListener {

    public Stat landSpeed;
    public Stat climbSpeed;
    public Stat flySpeed;
    public Stat burrowSpeed;
    public double speedPercRemaining;

    public SpeedComponent(Creature c) {
        addToCreature(c);
        landSpeed = new Stat();
        climbSpeed = new Stat();
        flySpeed = new Stat();
        burrowSpeed = new Stat();
    }

    @Override
    public Class<? extends Event>[] callOn() {
        return new Class[]{TurnStartEvent.class};
    }

    @Override
    public void onEvent(Event e) {
        speedPercRemaining = 1;
    }
}
