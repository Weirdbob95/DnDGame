package queries;

import grid.Square;

public class SquareQuery extends Query {

    public String desc;
    public Object source;
    public int range;
    public boolean los;
    public Square response;

    public SquareQuery(String desc, Object source, int range, boolean los) {
        this.desc = desc;
        this.source = source;
        this.range = range;
        this.los = los;
    }

}
