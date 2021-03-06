package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class AbstractEntity implements Serializable {

    public ArrayList<AbstractComponent> componentList;
    public ArrayList<AbstractSystem> systemList;
    public long id;

    public AbstractEntity() {
        id = (long) (Long.MAX_VALUE * Math.random());
        componentList = new ArrayList();
        systemList = new ArrayList();
        if (!(this instanceof GameManager)) {
            Core.gameManager.elc.add(this);
        }
    }

    public <E extends AbstractComponent> E add(E c) {
        componentList.add(c);
        if (!(this instanceof GameManager)) {
            Core.gameManager.elc.add(c);
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
        componentList.forEach(c -> c.destroy());
        systemList.forEach(s -> s.destroy());
        Core.gameManager.elc.remove(this);
    }

    public <E extends AbstractComponent> E getComponent(Class<E> e) {
        for (AbstractComponent c : componentList) {
            if (e.isInstance(c)) {
                return (E) c;
            }
        }
        return null;
    }

    public <C extends AbstractComponent> C getOrAdd(C c) {
        C r = (C) getComponent(c.getClass());
        if (r == null) {
            r = add(c);
        }
        return r;
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
