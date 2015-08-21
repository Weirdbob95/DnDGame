package amounts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class Value implements Amount {

    public int flat;
    public Die[] dice;

    public Value(int flat) {
        this(flat, new Die[0]);
    }

    public Value(Die... dice) {
        this(0, dice);
    }

    public Value(int flat, Die... dice) {
        this.flat = flat;
        this.dice = dice;
    }

    public Value add(Value other) {
        int newflat = flat + other.flat;
        List<Die> l = new ArrayList();// Arrays.asList(dice);
        l.addAll(Arrays.asList(dice));
        l.addAll(Arrays.asList(other.dice));
        return new Value(newflat, l.toArray(dice));
    }

    @Override
    public Value asValue() {
        return this;
    }

    public double average() {
        double r = flat;
        for (Die d : dice) {
            r += d.average();
        }
        return r;
    }

    @Override
    public int get() {
        int r = flat;
        for (Die d : dice) {
            r += d.get();
        }
        return r;
    }

    public static Value parseValue(String s) {
        Value r = new Value();
        ArrayList<Die> dice = new ArrayList();
        s = s.replaceAll("−", "-");
        s = s.replaceAll("-", "+-");
        for (String p : s.split("\\+")) {
            try {
                r.flat = Integer.parseInt(p);
            } catch (Exception e) {
                String[] a = p.split("d");
                for (int i = 0; i < Integer.parseInt(a[0]); i++) {
                    dice.add(new Die(Integer.parseInt(a[1]), false, false));
                }
            }
        }
        r.dice = dice.toArray(new Die[dice.size()]);
        return r;
    }

    @Override
    public int roll() {
        int r = flat;
        for (Die d : dice) {
            r += d.roll();
        }
        return r;
    }

    @Override
    public String toString() {
        /*
         String r = "[" + flat;
         for (Die d : dice) {
         r += ", d" + d.sides;
         }
         return r + "]";
         */
        TreeMap<Integer, Integer> dieMap = new TreeMap();
        for (Die d : dice) {
            if (!dieMap.containsKey(d.sides)) {
                dieMap.put(d.sides, 1);
            } else {
                dieMap.put(d.sides, dieMap.get(d.sides) + 1);
            }
        }
        String r = "";
        for (Integer i : dieMap.descendingKeySet()) {
            r += dieMap.get(i) + "d" + i + " + ";
        }
        if (flat == 0 && !dieMap.isEmpty()) {
            r = r.substring(0, r.length() - 3);
        } else {
            r += flat;
        }
        return r;
    }
}
