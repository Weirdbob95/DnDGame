package amounts;

public class CappedAmount implements Amount {

    public Amount amount;
    public Amount cap;

    public CappedAmount(Amount amount, Amount cap) {
        this.amount = amount;
        this.cap = cap;
    }

    @Override
    public Value asValue() {
        if (amount.get() > cap.get()) {
            return cap.asValue();
        } else {
            return amount.asValue();
        }
    }

    @Override
    public int get() {
        return Math.min(cap.get(), amount.get());
    }

    @Override
    public int roll() {
        return Math.min(cap.roll(), amount.roll());
    }
}
