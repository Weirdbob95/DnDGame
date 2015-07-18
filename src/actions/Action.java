package actions;

import creature.Creature;
import gui.UIAction;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Action implements Comparable {

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

    public abstract String description();

    public abstract Type getType();

    public boolean isAvaliable() {
        return true;
    }

    public void setUIParameters(UIAction uia) {
    }

    @Override
    public String toString() {
        return getClass().getSimpleName().replaceAll("Action", "");
    }

    public void use() {
        creature.amc.available.remove(getType());
//        new UseActionEvent(this);
        act();
    }

    public boolean validUIParameters() {
        return true;
    }
}
