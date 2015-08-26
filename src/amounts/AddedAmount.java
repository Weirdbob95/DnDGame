package amounts;

import java.util.Arrays;
import java.util.List;

public class AddedAmount implements Amount {

    public List<Amount> amounts;

    public AddedAmount(Amount... amounts) {
        this.amounts = Arrays.asList(amounts);
    }

    @Override
    public Value asValue() {
        return amounts.stream().map(Amount::asValue).reduce(new Value(), (a, b) -> a.add(b));
    }

    @Override
    public int get() {
        return amounts.stream().mapToInt(Amount::get).sum();
    }

    @Override
    public int roll() {
        return amounts.stream().mapToInt(Amount::roll).sum();
    }
}
