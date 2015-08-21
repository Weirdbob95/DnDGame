package amounts;

public class MultipliedAmount implements Amount {

    public Amount a;
    public double mult;

    public MultipliedAmount(Amount a, double mult) {
        this.a = a;
        this.mult = mult;
    }

    @Override
    public Value asValue() {
        return new Value(get());
    }

    @Override
    public int get() {
        return (int) (a.get() * mult);
    }

    @Override
    public int roll() {
        return (int) (a.roll() * mult);
    }
}
