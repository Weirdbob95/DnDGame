package actions;

import static actions.Action.Type.ACTION;
import creature.Creature;
import events.Event;
import events.EventHandler;
import events.EventListener;
import events.TurnStartEvent;
import events.attack.AttackRollEvent;

public class Dodge extends Action implements EventListener {

    public boolean dodging;

    public Dodge(Creature creature) {
        super(creature);
        EventHandler.addListener(this);
    }

    @Override
    public void act() {
        dodging = true;
    }

    @Override
    public Class<? extends Event>[] callOn() {
        return new Class[]{AttackRollEvent.class, TurnStartEvent.class};
    }

    @Override
    public String[] defaultTabs() {
        return new String[]{"Misc"};
    }

    @Override
    public String description() {
        return "When you take the Dodge action, you focus entirely on avoiding attacks. Until the start of your next turn, any attack roll made against you has disadvantage if you can see the attacker, and you make Dexterity saving throws with advantage. You lose this benefit if you are incapacitated or if your speed drops to 0.";
    }

    @Override
    public Type getType() {
        return ACTION;
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof AttackRollEvent) {
            if (((AttackRollEvent) e).a.target == creature) {
                ((AttackRollEvent) e).a.disadvantage = true;
            }
        }
        if (e instanceof TurnStartEvent) {
            if (((TurnStartEvent) e).creature == creature) {
                dodging = false;
            }
        }
    }

    @Override
    public double priority() {
        return 1;
    }
}
