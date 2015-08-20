package actions;

import creature.Creature;
import events.UseActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import util.Selectable;

public abstract class Action implements Comparable, Selectable, Serializable {

    public enum Type {

        ACTION, BONUS_ACTION, REACTION;
    }

    public Creature creature;
    public ArrayList<String> tabs;

    public Action(Creature creature) {
        this.creature = creature;
        tabs = new ArrayList(Arrays.asList(defaultTabs()));
        tabs.add("All");
    }

    protected abstract void act();

    @Override
    public int compareTo(Object t) {
        return toString().compareTo(t.toString());
    }

    public abstract String[] defaultTabs();

    @Override
    public String getName() {
        return getClass().getSimpleName().replaceAll("_", " ").replaceAll("Action", "");
    }

    public abstract Type getType();

    public boolean isAvailable() {
        return true;
    }

    public void use() {
        creature.amc.available.remove(getType());
        act();
        new UseActionEvent(this).call();
    }
}
