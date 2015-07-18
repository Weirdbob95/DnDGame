package amounts;

public class AddedAmount implements Amount {

    public Amount amount;
    public Amount add;

    public AddedAmount(Amount amount, Amount add) {
        this.amount = amount;
        this.add = add;
    }

    @Override
    public Value asValue() {
        return amount.asValue().add(add.asValue());
    }

    @Override
    public int get() {
        return amount.get() + add.get();
    }

    @Override
    public int roll() {
        return amount.roll() + add.roll();
    }

}
