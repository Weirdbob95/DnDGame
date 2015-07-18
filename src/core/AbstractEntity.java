package core;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class AbstractEntity {

    ArrayList<AbstractComponent> componentList;
    ArrayList<AbstractSystem> systemList;
    public long id;

    public AbstractEntity() {
        id = (long) (Long.MAX_VALUE * Math.random());
        componentList = new ArrayList();
        systemList = new ArrayList();
        if (!(this instanceof GameManager)) {
            Main.gameManager.elc.add(this);
        }
    }

    public <E extends AbstractComponent> E add(E c) {
        componentList.add(c);
        if (!(this instanceof GameManager)) {
            Main.gameManager.elc.add(c);
        }
        return c;
    }

    public <E extends AbstractSystem> E add(E s) {
        systemList.add(s);
        return s;
    }

    public void add(AbstractComponent... c) {
        componentList.addAll(Arrays.asList(c));
    }

    public void add(AbstractSystem... s) {
        systemList.addAll(Arrays.asList(s));
    }

    public void destroySelf() {
        for (AbstractComponent c : componentList) {
            c.destroy();
        }
        for (AbstractSystem s : systemList) {
            s.destroy();
        }
        Main.gameManager.elc.remove(this);
    }

    public <E extends AbstractComponent> E getComponent(Class<E> e) {
        for (AbstractComponent c : componentList) {
            if (e.isInstance(c)) {
                return (E) c;
            }
        }
        return null;
    }

    public <E extends AbstractSystem> E getSystem(Class<E> e) {
        for (AbstractSystem s : systemList) {
            if (e.isInstance(s)) {
                return (E) s;
            }
        }
        return null;
    }
}
