package amounts;

import java.util.Arrays;
import java.util.List;

public class MaxAmount implements Amount {

    public List<Amount> amounts;

    public MaxAmount(Amount... amounts) {
        this.amounts = Arrays.asList(amounts);
    }

    @Override
    public Value asValue() {
        return amounts.stream().max((a, b) -> a.get() - b.get()).get().asValue();
    }

    @Override
    public int get() {
        return amounts.stream().mapToInt(Amount::get).max().getAsInt();
    }

    @Override
    public int roll() {
        return amounts.stream().mapToInt(Amount::roll).min().getAsInt();
    }
}
