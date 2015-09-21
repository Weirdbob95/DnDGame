package util;

import java.io.Serializable;

public class Mutable<O extends Serializable> implements Serializable {

    public O o;

    public Mutable(O o) {
        this.o = o;
    }
}
