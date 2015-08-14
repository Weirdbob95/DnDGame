package animations;

import core.AbstractSystem;

public abstract class Animation extends AbstractSystem {

    public static Animation current;

    public Animation() {
        current = this;
    }

    public synchronized void start() {
        //System.out.println("Start");
        try {
            wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public synchronized void finish() {
        //System.out.println("Finish");
        notify();
        destroy();
        current = null;
    }
}
