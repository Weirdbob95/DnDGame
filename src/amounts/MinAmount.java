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
        return amounts.stream().min((a, b) -> a.get() - b.get()).get().asValue();
    }

    @Override
    public int get() {
        return amounts.stream().mapToInt(Amount::get).min().getAsInt();
    }

    @Override
    public int roll() {
        return amounts.stream().mapToInt(Amount::roll).min().getAsInt();
    }
}
