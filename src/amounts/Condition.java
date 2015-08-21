package amounts;

import java.io.Serializable;

@FunctionalInterface
public interface Condition extends Serializable {

    public boolean check();
}
