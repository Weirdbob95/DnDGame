package creature;

import amounts.MultiplierStat;
import core.AbstractComponent;
import events.EventListener;
import events.TurnStartEvent;

public class SpeedComponent extends AbstractComponent {

    public MultiplierStat landSpeed;
    public MultiplierStat climbSpeed;
    public MultiplierStat flySpeed;
    public MultiplierStat burrowSpeed;
    public double speedPercRemaining;

    public SpeedComponent(Creature c) {
        landSpeed = new MultiplierStat();
        climbSpeed = new MultiplierStat();
        flySpeed = new MultiplierStat();
        burrowSpeed = new MultiplierStat();

        EventListener.createListener(c, TurnStartEvent.class, e -> {
            if (e.creature == c) {
                speedPercRemaining = 1;
            }
        });
    }
}
