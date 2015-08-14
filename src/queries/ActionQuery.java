package queries;

import actions.Action;
import creature.Creature;
import ui.UIButton;
import ui.UIChooseButton;
import ui.UIText;

public class ActionQuery extends Query {

    public Creature creature;
    public Action response;

    public ActionQuery(Creature creature) {
        this.creature = creature;
    }

    @Override
    public void createUI() {
        new UIText(puic.root, "Choose an action");
        for (final Action a : creature.amc.allowedActions()) {
            UIButton optionButton = new UIButton(puic.root, a.toString());
            new UIText(optionButton, a.toString(), "Medium");
            new UIText(optionButton, a.description(), "Small");
            new UIChooseButton(optionButton, "Use action", this) {
                @Override
                public void act() {
                    response = a;
                }
            };
        }
        new UIChooseButton(puic.root, "End turn", this);
    }

}
