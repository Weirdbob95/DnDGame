package events.attack;

import events.Event;

public class AttackDamageResultEvent extends Event {

    public AttackEvent a;

    public AttackDamageResultEvent(AttackEvent a) {
        this.a = a;
    }
}
