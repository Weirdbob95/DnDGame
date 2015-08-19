package core;

public abstract class AbstractSystem {

    public AbstractSystem() {
        Core.systems.get(getLayer()).add(this);
    }

    public void destroy() {
        Core.systems.get(getLayer()).remove(this);
    }

    protected int getLayer() {
        return 1;
    }

    protected boolean pauseable() {
        return false;
    }

    public abstract void update();
}
