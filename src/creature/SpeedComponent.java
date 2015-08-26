package creature;

import amounts.Stat;
import core.AbstractComponent;
import events.EventListener;
import events.TurnStartEvent;

public class SpeedComponent extends AbstractComponent {

    public Stat landSpeed;
    public Stat climbSpeed;
    public Stat flySpeed;
    public Stat burrowSpeed;
    public double speedPercRemaining;

    public SpeedComponent(Creature c) {
        landSpeed = new Stat();
        climbSpeed = new Stat();
        flySpeed = new Stat();
        burrowSpeed = new Stat();

        EventListener.createListener(c, TurnStartEvent.class, e -> {
            if (e.creature == c) {
                speedPercRemaining = 1;
            }
        });
    }
}
