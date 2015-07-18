package amounts;

public class Die implements Amount {

    public final int sides;
    public final boolean av;
    public final boolean disav;
    public int roll;

    public Die(int sides) {
        this.sides = sides;
        this.av = false;
        this.disav = false;
        roll = (int) (Math.random() * sides) + 1;
    }

    public Die(int sides, boolean av, boolean disav) {
        this.sides = sides;
        this.av = av;
        this.disav = disav;
        if (av && !disav) {
            roll = Math.max((int) (Math.random() * sides) + 1, (int) (Math.random() * sides) + 1);
        } else if (disav && !av) {
            roll = Math.min((int) (Math.random() * sides) + 1, (int) (Math.random() * sides) + 1);
        } else {
            roll = (int) (Math.random() * sides) + 1;
        }
    }

    @Override
    public Value asValue() {
        return new Value(this);
    }

    public double average() {
        return (sides + 1) / 2.;
    }

    public static Value d(int n, int x) {
        Value v = new Value();
        v.dice = new Die[n];
        for (int i = 0; i < n; i++) {
            v.dice[i] = new Die(x);
        }
        return v;
    }

    @Override
    public int get() {
        return roll;
    }

    @Override
    public int roll() {
        return roll = (int) (Math.random() * sides) + 1;
    }
}
