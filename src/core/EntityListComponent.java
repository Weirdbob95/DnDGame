package core;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityListComponent extends AbstractComponent {

    private ArrayList<AbstractEntity> list;
    private HashMap<Class, ArrayList> map;

    public EntityListComponent() {
        list = new ArrayList();
        map = new HashMap();
    }

    public void add(AbstractEntity e) {
        if (map.keySet().contains(e.getClass())) {
            map.get(e.getClass()).add(e);
        }
        list.add(e);
    }

    public void add(AbstractComponent c) {
        if (map.keySet().contains(c.getClass())) {
            map.get(c.getClass()).add(c);
        }
    }

    public <E extends AbstractComponent> ArrayList<E> getComponentList(Class<E> c) {
        if (map.keySet().contains(c)) {
            return (ArrayList<E>) map.get(c);
        }
        ArrayList<E> r = new ArrayList();
        for (AbstractEntity e : list) {
            for (AbstractComponent ac : e.componentList) {
                if (c.isInstance(ac)) {
                    r.add((E) ac);
                }
            }
        }
        return r;
    }

    public <E extends AbstractEntity> E getEntity(Class<E> c) {
        if (map.keySet().contains(c)) {
            return (E) map.get(c).get(0);
        }
        for (AbstractEntity e : list) {
            if (c.isInstance(e)) {
                return (E) e;
            }
        }
        return null;
    }

    public <E extends AbstractEntity> ArrayList<E> getEntityList(Class<E> c) {
        if (map.keySet().contains(c)) {
            return (ArrayList<E>) map.get(c);
        }
        ArrayList<E> r = new ArrayList();
        for (AbstractEntity e : list) {
            if (c.isInstance(e)) {
                r.add((E) e);
            }
        }
        return r;
    }

    public AbstractEntity getId(long id) {
        for (AbstractEntity e : list) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }

    public void remove(AbstractEntity e) {
        if (map.keySet().contains(e.getClass())) {
            map.get(e.getClass()).remove(e);
        }
        list.remove(e);
    }

    public void remove(AbstractComponent c) {
        if (map.keySet().contains(c.getClass())) {
            map.get(c.getClass()).remove(c);
        }
    }
}
