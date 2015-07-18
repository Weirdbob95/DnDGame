package events;

public interface EventListener {

    public Class<? extends Event>[] callOn();

    public void onEvent(Event e);

    public double priority();
}
