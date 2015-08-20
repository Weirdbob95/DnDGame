package core;

import java.io.Serializable;

public abstract class AbstractComponent implements Serializable {

    protected void destroy() {
        Core.gameManager.elc.remove(this);
    }
}
