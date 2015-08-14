package creature;

import core.AbstractSystem;
import graphics.Graphics2D;
import grid.Square;
import movement.PositionComponent;
import util.Color4d;
import util.Vec2;

public class HealthbarSystem extends AbstractSystem {

    private PositionComponent pc;
    private HealthComponent hc;
    private CreatureDescriptionComponent cdc;

    public HealthbarSystem(PositionComponent pc, HealthComponent hc, CreatureDescriptionComponent cdc) {
        this.pc = pc;
        this.hc = hc;
        this.cdc = cdc;
    }

    @Override
    public void update() {
        double scale = cdc.size.squares / 2.3 * Square.SIZE;
        Vec2 LL = pc.pos.subtract(new Vec2(scale, scale));
        double healthPerc = (double) hc.currentHealth.get() / hc.maxHealth.get();
        if (healthPerc < 0) {
            healthPerc = 0;
        }
        if (healthPerc > 1) {
            healthPerc = 1;
        }
        Graphics2D.fillRect(LL, new Vec2(scale * 2, scale / 5), Color4d.BLACK);
        Graphics2D.fillRect(LL, new Vec2(scale * 2 * healthPerc, scale / 5), new Color4d(1 - healthPerc, healthPerc, 0));
    }
}
