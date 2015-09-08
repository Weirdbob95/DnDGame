package actions;

import creature.Creature;
import events.EventListenerContainer;
import events.UseActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import util.Selectable;

public abstract class Action extends EventListenerContainer implements Comparable, Selectable {

    public enum Type {

        ACTION, BONUS_ACTION, REACTION;
    }

    public Creature creature;
    public ArrayList<String> tabs;

    public Action(Creature creature) {
        super(creature);
        this.creature = creature;
        tabs = new ArrayList(Arrays.asList(defaultTabs()));
        tabs.add("All");
    }

    protected abstract void act();

    public void add() {
        setEnabled(true);
    }

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

    public void remove() {
        setEnabled(false);
    }

    public void use() {
        creature.amc.useType(getType(), this);
        new UseActionEvent(this).call();
        act();
    }

    public void useNoType() {
        new UseActionEvent(this).call();
        act();
    }
}
