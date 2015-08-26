package actions;

import static actions.Action.Type.ACTION;
import creature.Creature;
import events.TurnStartEvent;
import events.attack.AttackRollEvent;

public class Dodge extends Action {

    public boolean dodging;

    public Dodge(Creature creature) {
        super(creature);

        add(AttackRollEvent.class, e -> {
            if (e.a.target == creature && dodging) {
                e.a.disadvantage = true;
            }
        });
        add(TurnStartEvent.class, e -> {
            if (e.creature == creature) {
                dodging = false;
            }
        });
    }

    @Override
    public void act() {
        dodging = true;
    }

    @Override
    public String[] defaultTabs() {
        return new String[]{"Misc"};
    }

    @Override
    public String getDescription() {
        return "When you take the Dodge action, you focus entirely on avoiding attacks. Until the start of your next turn, any attack roll made against you has disadvantage if you can see the attacker, and you make Dexterity saving throws with advantage. You lose this benefit if you are incapacitated or if your speed drops to 0.";
    }

    @Override
    public Type getType() {
        return ACTION;
    }
}
