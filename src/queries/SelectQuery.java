package queries;

import ui.UIButton;
import ui.UIChooseButton;
import ui.UIText;
import java.util.Arrays;
import java.util.List;

public class SelectQuery<E> extends Query {

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
            UIButton optionButton = new UIButton(puic.root, option.toString());
            new UIText(optionButton, option.toString());
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
