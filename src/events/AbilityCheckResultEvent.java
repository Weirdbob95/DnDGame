package events;

public class AbilityCheckResultEvent extends Event {

    public AbilityCheckEvent ace;

    public AbilityCheckResultEvent(AbilityCheckEvent ace) {
        this.ace = ace;
    }
}
