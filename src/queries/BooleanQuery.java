package queries;

import ui.UIChooseButton;
import ui.UIText;

public class BooleanQuery extends Query {

    public String desc;
    public boolean response;

    public BooleanQuery(String desc) {
        this.desc = desc;
    }

    @Override
    public void createUI() {
        new UIText(puic.root, desc);
        new UIChooseButton(puic.root, "Yes", this) {
            @Override
            public void act() {
                response = true;
            }
        };
        new UIChooseButton(puic.root, "No", this);
    }
}
