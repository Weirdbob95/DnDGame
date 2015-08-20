package controllers;

import java.io.Serializable;
import queries.Query;

public interface CreatureController extends Serializable {

    public void handleQuery(Query query);

    public void turn();
}
