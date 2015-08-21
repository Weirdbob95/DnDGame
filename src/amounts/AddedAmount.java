package amounts;

public class AddedAmount implements Amount {

    public Amount[] amounts;

    public AddedAmount(Amount... amounts) {
        this.amounts = amounts;
    }

    @Override
    public Value asValue() {
        Value r = new Value();
        for (Amount a : amounts) {
            r = r.add(a.asValue());
        }
        return r;
    }

    @Override
    public int get() {
        int r = 0;
        for (Amount a : amounts) {
            r += a.get();
        }
        return r;
    }

    @Override
    public int roll() {
        int r = 0;
        for (Amount a : amounts) {
            r += a.roll();
        }
        return r;
    }
}
