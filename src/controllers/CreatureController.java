package controllers;

import queries.Query;

public interface CreatureController {

    public boolean go();

    public void handleQuery(Query query);
}
