package animations;

import core.AbstractSystem;

public abstract class Animation extends AbstractSystem {

    public static Animation current;

    public Animation() {
        current = this;
    }

    public synchronized void start() {
        try {
            wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public synchronized void finish() {
        notify();
        destroy();
        current = null;
    }
}
