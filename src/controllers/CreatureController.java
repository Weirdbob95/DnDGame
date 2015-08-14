package controllers;

import queries.Query;

public interface CreatureController {

    public void handleQuery(Query query);

    public void turn();
}
