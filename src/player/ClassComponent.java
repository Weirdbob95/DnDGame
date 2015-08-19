package player;

import classes.PlayerClass;
import core.AbstractComponent;
import creature.Creature;
import java.util.ArrayList;
import util.Log;

public class ClassComponent extends AbstractComponent {

    public ArrayList<PlayerClass> classes = new ArrayList();
    public Player player;

    public ClassComponent(Player player) {
        this.player = player;
    }

    public void addLevel(String name) {
        try {
            Class c = Class.forName("classes." + name);
            for (PlayerClass pc : classes) {
                if (c.isInstance(pc)) {
                    pc.levelTo(pc.level + 1);
                    return;
                }
            }
            PlayerClass pc = (PlayerClass) c.getConstructor(Creature.class).newInstance(player);
            pc.levelTo(1);
        } catch (Exception ex) {
            Log.error(ex);
        }
    }
}
