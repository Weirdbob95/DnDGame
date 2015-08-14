package ui;

import queries.Query;

public class UIChooseButton extends UIButton {

    public Query query;

    public UIChooseButton(UIItem parent, String text, Query query) {
        super(parent, text);
        this.query = query;
    }

    public void act() {
    }

    @Override
    public void onClick() {
        act();
        query.removeFromUI();
    }

}
