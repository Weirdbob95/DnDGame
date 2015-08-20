package controllers;

import actions.Action;
import core.Core;
import creature.Creature;
import queries.Query;
import queries.SelectQuery;
import ui.PlayerUIComponent;
import util.Log;

public class ManualController implements CreatureController {

    private PlayerUIComponent puic;
    private Creature creature;

    public ManualController(Creature creature) {
        this.creature = creature;
        puic = Core.gameManager.getComponent(PlayerUIComponent.class);
    }

    @Override
    public synchronized void handleQuery(Query query) {
        if (query.addToUI(this)) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Log.error(ex);
            }
        }
    }

    public synchronized void synNotify() {
        notify();
    }

    @Override
    public void turn() {
        while (true) {
            Action a = Query.ask(creature, new SelectQuery<Action>("Choose an action", creature.amc.allowedActions(), "Use Action", "End Turn")).response;
            if (a != null) {
                a.use();
            } else {
                break;
            }
        }
    }
}
