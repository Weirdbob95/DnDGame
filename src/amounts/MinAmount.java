package amounts;

import java.util.Arrays;
import java.util.List;

public class MinAmount implements Amount {

    public List<Amount> amounts;

    public MinAmount(Amount... amounts) {
        this.amounts = Arrays.asList(amounts);
    }

    @Override
    public Value asValue() {
        return amounts.stream().reduce((a, b) -> a.get() < b.get() ? a : b).get().asValue();
    }

    @Override
    public int get() {
        return amounts.stream().mapToInt(Amount::get).reduce(Math::min).getAsInt();
    }

    @Override
    public int roll() {
        return amounts.stream().mapToInt(Amount::roll).reduce(Math::min).getAsInt();
    }
}
