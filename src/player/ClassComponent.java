package player;

import classes.PlayerClass;
import core.AbstractComponent;
import java.util.ArrayList;
import util.Log;

public class ClassComponent extends AbstractComponent {

    public Player player;
    public ArrayList<PlayerClass> classes = new ArrayList();

    public ClassComponent(Player player) {
        this.player = player;
    }

    public void addLevel(String name) {
        try {
            Class c = Class.forName("classes." + name.toLowerCase() + "." + name);
            for (PlayerClass pc : classes) {
                if (c.isInstance(pc)) {
                    pc.levelTo(pc.level + 1);
                    return;
                }
            }
            PlayerClass pc = (PlayerClass) c.getConstructor(Player.class).newInstance(player);
            classes.add(pc);
            pc.levelTo(1);
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    public int level() {
        int r = 0;
        for (PlayerClass pc : classes) {
            r += pc.level;
        }
        return r;
    }
}
