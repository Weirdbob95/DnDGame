package creature;

import amounts.MultiplierStat;
import core.AbstractComponent;
import events.EventListener;
import events.TurnStartEvent;

public class SpeedComponent extends AbstractComponent {

    public static final String EXTRA_COST = "Extra Cost";
    public static final String BASE = "Base";

    public MultiplierStat landSpeed;
    public MultiplierStat climbSpeed;
    public MultiplierStat swimSpeed;
    public MultiplierStat flySpeed;
    public MultiplierStat burrowSpeed;
    public double speedPercRemaining;

    public SpeedComponent(Creature c) {
        landSpeed = new MultiplierStat();
        climbSpeed = new MultiplierStat();
        swimSpeed = new MultiplierStat();
        flySpeed = new MultiplierStat();
        burrowSpeed = new MultiplierStat();

        climbSpeed.flatComponents.put(BASE, landSpeed);
        climbSpeed.multComponents.put(EXTRA_COST, .5);
        swimSpeed.flatComponents.put(BASE, landSpeed);
        swimSpeed.multComponents.put(EXTRA_COST, .5);

        EventListener.createListener(c, TurnStartEvent.class, e -> {
            if (e.creature == c) {
                speedPercRemaining = 1;
            }
        });
    }
}
