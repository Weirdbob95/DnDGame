package controllers;

import actions.Action;
import core.Main;
import creature.Creature;
import ui.PlayerUIComponent;
import queries.ActionQuery;
import queries.Query;

public class ManualController implements CreatureController {

    private PlayerUIComponent puic;
    private Creature creature;

    public ManualController(Creature creature) {
        this.creature = creature;
        puic = Main.gameManager.getComponent(PlayerUIComponent.class);
    }

//    public boolean go() {
//        if (creature.amc.allowedActions().size() <= 1) {
//            return true;
//        }
//        if (puic.root == null) {
//            puic.root = new UIItem(null) {
//                @Override
//                public void draw() {
//                }
//
//                @Override
//                public void onClick() {
//                }
//            };
//            HashMap<String, HashSet<Action>> tabbedActions = new HashMap();
//            HashMap<String, UIItem> uiTabs = new HashMap();
//            tabbedActions.put("", new HashSet());
//            uiTabs.put("", puic.root);
//            for (Action a : creature.amc.allowedActions()) {
//                for (String s : a.tabs) {
//                    if (!tabbedActions.containsKey(s)) {
//                        tabbedActions.put(s, new HashSet());
//                        uiTabs.put(s, new UIButton(puic.root, s));
//                    }
//                    tabbedActions.get(s).add(a);
//                }
//            }
//            for (String s : tabbedActions.keySet()) {
//                for (Action a : tabbedActions.get(s)) {
//                    new UIAction(uiTabs.get(s), a);
//                }
//            }
//            for (UIItem i : uiTabs.values()) {
//                if (i.getClass() == UIButton.class) {
//                    Collections.sort(i.children, new Comparator() {
//                        @Override
//                        public int compare(Object t, Object t1) {
//                            return ((UIButton) t).text.compareTo(((UIButton) t1).text);
//                        }
//                    });
//                }
//            }
//            Collections.sort(puic.root.children, new Comparator() {
//                @Override
//                public int compare(Object t, Object t1) {
//                    if (t instanceof UIAction && t1 instanceof UIAction) {
//                        return ((UIAction) t).a.compareTo(((UIAction) t1).a);
//                    }
//                    if (t instanceof UIAction && t1 instanceof UIButton) {
//                        return 1;
//                    }
//                    if (t instanceof UIButton && t1 instanceof UIAction) {
//                        return -1;
//                    }
//                    if (t instanceof UIButton && t1 instanceof UIButton) {
//                        return ((UIButton) t).text.compareTo(((UIButton) t1).text);
//                    }
//                    return 0;
//                }
//            });
//        }
//        return false;
//    }
    @Override
    public synchronized void handleQuery(Query query) {
        query.addToUI(this);
        try {
            wait();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

    public synchronized void synNotify() {
        notify();
    }

    @Override
    public void turn() {
        while (true) {
            Action a = Query.ask(creature, new ActionQuery(creature)).response;
            if (a != null) {
                a.use();
            } else {
                break;
            }
        }
    }
}
