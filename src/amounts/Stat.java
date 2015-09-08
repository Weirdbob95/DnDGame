package amounts;

import java.util.HashMap;

public class Stat implements Amount {

    private static final String BASE_STRING = "Base";
    public final HashMap<String, Amount> components = new HashMap();

    public Stat() {
    }

    public Stat(int base) {
        components.put(BASE_STRING, new Value(base));
    }

    public Stat(String name, Amount a) {
        components.put(name, a);
    }

    @Override
    public Value asValue() {
        return components.values().stream().map(Amount::asValue).reduce(new Value(), (a, b) -> a.add(b));
    }

    public Stat edit(int a) {
        return edit(BASE_STRING, a);
    }

    public Stat edit(String name, int a) {
        if (!components.containsKey(name)) {
            components.put(name, new Value(a));
        } else {
            set(name, components.get(name).get() + a);
        }
        return this;
    }

    @Override
    public int get() {
        return components.values().stream().mapToInt(Amount::get).sum();
    }

    @Override
    public int roll() {
        return components.values().stream().mapToInt(Amount::roll).sum();
    }

    public Stat set(int a) {
        return set(BASE_STRING, a);
    }

    public Stat set(String name, int a) {
        components.put(name, new Value(a));
        return this;
    }

    public Stat set(String name, Amount a) {
        components.put(name, a);
        return this;
    }

    @Override
    public String toString() {
        return "" + get();
    }
}
