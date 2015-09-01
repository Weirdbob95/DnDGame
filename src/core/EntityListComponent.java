package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

    public <E extends AbstractComponent> List<E> getComponentList(Class<E> c) {
        if (map.keySet().contains(c)) {
            return (ArrayList<E>) map.get(c);
        }
        return list.stream().flatMap(e -> e.componentList.stream()).filter(c::isInstance).map(e -> (E) e).collect(Collectors.toList());
    }

    public <E extends AbstractEntity> E getEntity(Class<E> c) {
        if (map.keySet().contains(c)) {
            return (E) map.get(c).get(0);
        }
        return list.stream().filter(c::isInstance).map(e -> (E) e).findFirst().orElse(null);
    }

    public <E extends AbstractEntity> List<E> getEntityList(Class<E> c) {
        if (map.keySet().contains(c)) {
            return (ArrayList<E>) map.get(c);
        }
        return list.stream().filter(c::isInstance).map(e -> (E) e).collect(Collectors.toList());
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
