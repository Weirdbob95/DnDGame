package events.attack;

import events.Event;

public class AttackFinishEvent extends Event {

    public AttackEvent a;
    public boolean hit;

    public AttackFinishEvent(AttackEvent a, boolean hit) {
        this.a = a;
        this.hit = hit;
    }
}
