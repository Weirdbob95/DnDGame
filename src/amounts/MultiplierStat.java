package amounts;

import java.util.HashMap;

public class MultiplierStat implements Amount {

    public final HashMap<Object, Amount> flatComponents = new HashMap();
    public final HashMap<Object, DoubleAmount> multComponents = new HashMap();

    @Override
    public int get() {
        return (int) (flatComponents.values().stream().mapToInt(Amount::get).sum() * multComponents.values().stream().mapToDouble(DoubleAmount::get).reduce(1., (a, b) -> a * b));
    }

    @Override
    public int roll() {
        return (int) (flatComponents.values().stream().mapToInt(Amount::roll).sum() * multComponents.values().stream().mapToDouble(DoubleAmount::get).reduce(1., (a, b) -> a * b));
    }

    public void set(int amt) {
        flatComponents.put("Base", new Value(amt));
    }
}
