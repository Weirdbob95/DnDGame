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
    public String chooseString;
    public String cancelString;
    public E response;

    public SelectQuery(String desc, E... options) {
        this.desc = desc;
        this.options = Arrays.asList(options);
        chooseString = "Choose";
    }

    public SelectQuery(String desc, List<E> options) {
        this.desc = desc;
        this.options = options;
        chooseString = "Choose";
    }

    public SelectQuery(String desc, List<E> options, String chooseString, String cancelString) {
        this.desc = desc;
        this.options = options;
        this.chooseString = chooseString;
        this.cancelString = cancelString;
    }

    @Override
    public boolean createUI() {
        if (options.size() <= 1 && cancelString == null) {
            response = options.isEmpty() ? null : options.get(0);
            return false;
        }
        new UIText(puic.root, desc);
        for (final E option : options) {
            UIButton optionButton = new UIButton(puic.root, option.getName());
            new UIText(optionButton, option.getName(), "Medium");
            new UIText(optionButton, option.getDescription(), "Small");
            new UIChooseButton(optionButton, chooseString, this) {
                @Override
                public void act() {
                    response = option;
                }
            };
        }
        if (cancelString != null) {
            new UIChooseButton(puic.root, cancelString, this);
        }
        return true;
    }
}
