package player;

import backgrounds.Background;
import core.AbstractComponent;
import enums.Skill;
import util.Log;
import util.Util;

public class BackgroundComponent extends AbstractComponent {

    public Player player;
    public Background background;

    public BackgroundComponent(Player player) {
        this.player = player;
    }

    public void setBackground(String name) {
        try {
            background = (Background) Util.nameToClass("backgrounds", name).newInstance();
            for (Skill s : background.skills()) {
                if (!player.pc.skillProfs.add(s)) {
                    player.pc.chooseSkill(Skill.values());
                }
            }
        } catch (Exception ex) {
            Log.print(ex);
        }
    }
}
