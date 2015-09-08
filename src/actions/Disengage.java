package actions;

import static actions.Action.Type.ACTION;
import creature.Creature;
import events.TurnStartEvent;
import events.attack.OpportunityAttackEvent;

public class Disengage extends Action {

    public boolean disengaging;

    public Disengage(Creature creature) {
        super(creature);

        add(OpportunityAttackEvent.class, e -> {
            if (e.a.target == creature && disengaging) {
                e.blockers.add("Disengage");
            }
        });
        add(TurnStartEvent.class, e -> {
            if (e.creature == creature) {
                disengaging = false;
            }
        });
    }

    @Override
    protected void act() {
        disengaging = true;
    }

    @Override
    public String[] defaultTabs() {
        return new String[]{};
    }

    @Override
    public String getDescription() {
        return "If you take the Disengage action, your movement doesn't provoke opportunity attacks for the rest of your turn.";
    }

    @Override
    public Type getType() {
        return ACTION;
    }
}
