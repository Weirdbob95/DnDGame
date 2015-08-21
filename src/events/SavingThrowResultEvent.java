package events;

public class SavingThrowResultEvent extends Event {

    public SavingThrowEvent ste;

    public SavingThrowResultEvent(SavingThrowEvent ste) {
        this.ste = ste;
    }
}
