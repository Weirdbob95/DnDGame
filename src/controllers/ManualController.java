package controllers;

import actions.Action;
import core.Main;
import creature.Creature;
import gui.PlayerUIComponent;
import gui.UIAction;
import gui.UIButton;
import gui.UIItem;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import queries.Query;
import queries.SquareQuery;

public class ManualController implements CreatureController {

    private PlayerUIComponent puic;
    private Creature creature;

    public ManualController(Creature creature) {
        this.creature = creature;
        puic = Main.gameManager.getComponent(PlayerUIComponent.class);
    }

    @Override
    public boolean go() {
        if (creature.amc.allowedActions().size() <= 1) {
            return true;
        }
        if (puic.selected == null) {
            puic.selected = new UIItem(null) {
                @Override
                public void draw() {
                }

                @Override
                public void onClick() {
                }
            };
            HashMap<String, HashSet<Action>> tabbedActions = new HashMap();
            HashMap<String, UIItem> uiTabs = new HashMap();
            tabbedActions.put("", new HashSet());
            uiTabs.put("", puic.selected);
            for (Action a : creature.amc.allowedActions()) {
                for (String s : a.tabs) {
                    if (!tabbedActions.containsKey(s)) {
                        tabbedActions.put(s, new HashSet());
                        uiTabs.put(s, new UIButton(puic.selected, s));
                    }
                    tabbedActions.get(s).add(a);
                }
            }
            for (String s : tabbedActions.keySet()) {
                for (Action a : tabbedActions.get(s)) {
                    new UIAction(uiTabs.get(s), a);
                }
            }
            for (UIItem i : uiTabs.values()) {
                if (i.getClass() == UIButton.class) {
                    Collections.sort(i.children, new Comparator() {
                        @Override
                        public int compare(Object t, Object t1) {
                            return ((UIButton) t).text.compareTo(((UIButton) t1).text);
                        }
                    });
                }
            }
            Collections.sort(puic.selected.children, new Comparator() {
                @Override
                public int compare(Object t, Object t1) {
                    if (t instanceof UIAction && t1 instanceof UIAction) {
                        return ((UIAction) t).a.compareTo(((UIAction) t1).a);
                    }
                    if (t instanceof UIAction && t1 instanceof UIButton) {
                        return 1;
                    }
                    if (t instanceof UIButton && t1 instanceof UIAction) {
                        return -1;
                    }
                    if (t instanceof UIButton && t1 instanceof UIButton) {
                        return ((UIButton) t).text.compareTo(((UIButton) t1).text);
                    }
                    return 0;
                }
            });
        }
        return false;
    }

    @Override
    public void handleQuery(Query query) {
        if (query instanceof SquareQuery) {

        }
    }
}
