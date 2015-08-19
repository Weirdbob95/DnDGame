package queries;

import java.util.Arrays;
import java.util.List;
import ui.UIButton;
import ui.UIChooseButton;
import ui.UIText;
import util.Selectable;

public class SelectQuery<E extends Selectable> extends Query {

    public String desc;
    public List<E> options;
    public E response;

    public SelectQuery(String desc, E... options) {
        this.desc = desc;
        this.options = Arrays.asList(options);
    }

    public SelectQuery(String desc, List<E> options) {
        this.desc = desc;
        this.options = options;
    }

    @Override
    public void createUI() {
        new UIText(puic.root, desc);
        for (final E option : options) {
            UIButton optionButton = new UIButton(puic.root, option.getName());
            new UIText(optionButton, option.getName(), "Medium");
            new UIText(optionButton, option.getDescription(), "Small");
            new UIChooseButton(optionButton, "Choose", this) {
                @Override
                public void act() {
                    response = option;
                }
            };
        }
        new UIChooseButton(puic.root, "Cancel", this);
    }
}
