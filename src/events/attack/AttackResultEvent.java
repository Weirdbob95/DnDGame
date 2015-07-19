package events.attack;

import events.Event;

public class AttackResultEvent extends Event {

    public AttackEvent a;

    public AttackResultEvent(AttackEvent a) {
        this.a = a;
    }
}
