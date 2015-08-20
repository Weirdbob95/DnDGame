package amounts;

import java.io.Serializable;

public interface Amount extends Serializable {

    public Value asValue();

    public int get();

    public int roll();
}
