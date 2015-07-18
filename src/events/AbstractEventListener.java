package events;

public abstract class AbstractEventListener implements EventListener {

    public AbstractEventListener() {
        EventHandler.addListener(this);
    }

    @Override
    public double priority() {
        return 0;
    }
}
