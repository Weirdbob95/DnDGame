package queries;

import ui.UIChooseButton;
import ui.UIText;
import ui.UIValue;

public class IntegerQuery extends UpdatingQuery {

    public String text;
    public String label;
    public int min;
    public int max;
    public int response;
    public UIValue uiValue;

    public IntegerQuery(String text, String label, int min, int max) {
        this.text = text;
        this.label = label;
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean createUI() {
        new UIText(puic.root, text);
        uiValue = new UIValue(puic.root, this);
        uiValue.value = min;
        new UIChooseButton(puic.root, "Finish", this);
        updateUI();
        return true;
    }

    @Override
    public void updateUI() {
        response = uiValue.value;
        uiValue.text = label + ": " + response;
        uiValue.minus.disabled = response == min;
        uiValue.plus.disabled = response == max;
    }
}
