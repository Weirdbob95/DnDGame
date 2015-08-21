package amounts;

public class ConditionalAmount implements Amount {

    public Condition condition;
    public Amount a1;
    public Amount a2;

    public ConditionalAmount(Condition condition, Amount a1, Amount a2) {
        this.condition = condition;
        this.a1 = a1;
        this.a2 = a2;
    }

    public ConditionalAmount(Condition condition, Amount a1) {
        this(condition, a1, new Value());
    }

    @Override
    public Value asValue() {
        if (condition.check()) {
            return a1.asValue();
        } else {
            return a2.asValue();
        }
    }

    @Override
    public int get() {
        if (condition.check()) {
            return a1.get();
        } else {
            return a2.get();
        }
    }

    @Override
    public int roll() {
        if (condition.check()) {
            return a1.roll();
        } else {
            return a2.roll();
        }
    }
}
