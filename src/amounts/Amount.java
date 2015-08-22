package amounts;

import java.io.Serializable;

@FunctionalInterface
public interface Amount extends Serializable {

    public default Value asValue() {
        return new Value(get());
    }

    public int get();

    public default int roll() {
        return get();
    }
}
