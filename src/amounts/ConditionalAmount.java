package amounts;

import java.util.concurrent.Callable;

public class ConditionalAmount implements Amount {

    public Callable<Boolean> condition;
    public Amount a1;
    public Amount a2;

    public ConditionalAmount(Callable<Boolean> condition, Amount a1, Amount a2) {
        this.condition = condition;
        this.a1 = a1;
        this.a2 = a2;
    }

    public ConditionalAmount(Callable<Boolean> condition, Amount a1) {
        this(condition, a1, new Value());
    }

    @Override
    public Value asValue() {
        try {
            if (condition.call()) {
                return a1.asValue();
            }
        } catch (Exception ex) {
        }
        return a2.asValue();
    }

    @Override
    public int get() {
        try {
            if (condition.call()) {
                return a1.get();
            }
        } catch (Exception ex) {
        }
        return a2.get();
    }

    @Override
    public int roll() {
        try {
            if (condition.call()) {
                return a1.roll();
            }
        } catch (Exception ex) {
        }
        return a2.roll();
    }

}
