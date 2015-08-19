package ui;

import graphics.Graphics2D;
import queries.PointBuyQuery;
import util.Color4d;
import util.Vec2;

public class UIPointBuy extends UIText {

    public UIButton minus;
    public UIButton plus;
    public int value;
    public PointBuyQuery query;

    public UIPointBuy(UIItem parent, PointBuyQuery query) {
        super(parent, "", "Medium");
        this.query = query;
        minus = new UIButton(null, "") {
            @Override
            public void draw() {
                super.draw();
                Graphics2D.fillRect(pos.add(size.multiply(new Vec2(.1, .4))), size.multiply(new Vec2(.8, .2)), Color4d.BLACK);
            }
        };
        minus.size = new Vec2(20, -20);
        plus = new UIButton(null, "") {
            @Override
            public void draw() {
                super.draw();
                Graphics2D.fillRect(pos.add(size.multiply(new Vec2(.1, .4))), size.multiply(new Vec2(.8, .2)), Color4d.BLACK);
                Graphics2D.fillRect(pos.add(size.multiply(new Vec2(.4, .1))), size.multiply(new Vec2(.2, .8)), Color4d.BLACK);
            }
        };
        plus.size = new Vec2(20, -20);
        size = size.setY(-30);
    }

    @Override
    public void draw() {
        super.draw();
        minus.pos = pos.add(size).subtract(new Vec2(55, -25));
        minus.draw();
        plus.pos = pos.add(size).subtract(new Vec2(25, -25));
        plus.draw();
    }

    @Override
    public void onClick() {
        if (minus.selected()) {
            value--;
            query.updateUI();
        }
        if (plus.selected()) {
            value++;
            query.updateUI();
        }
    }
}
