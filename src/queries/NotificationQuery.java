package queries;

import ui.UIChooseButton;
import ui.UIText;

public class NotificationQuery extends Query {

    public String desc;

    public NotificationQuery(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean createUI() {
        new UIText(puic.root, desc);
        new UIChooseButton(puic.root, "OK", this);
        return true;
    }
}
