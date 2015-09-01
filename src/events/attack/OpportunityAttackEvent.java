package events.attack;

import events.Event;
import java.util.ArrayList;

public class OpportunityAttackEvent extends Event {

    public static final String ENTERING_REACH = "Entering Reach";
    public static final String LEAVING_REACH = "Leaving Reach";

    public AttackEvent a;
    public String cause;
    public ArrayList<String> blockers;

    public OpportunityAttackEvent(AttackEvent a, String cause) {
        this.a = a;
        this.cause = cause;
        blockers = new ArrayList();
        a.isOpportunityAttack = true;
    }

    @Override
    public void call() {
        super.call();
        if (blockers.isEmpty()) {
            a.call();
        }
    }
}
