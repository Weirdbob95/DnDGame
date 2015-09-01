package amounts;

import java.io.Serializable;

@FunctionalInterface
public interface Conditional extends Serializable {

    public boolean check();
}
