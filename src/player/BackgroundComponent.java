package player;

import backgrounds.Background;
import core.AbstractComponent;
import enums.Skill;
import util.Log;

public class BackgroundComponent extends AbstractComponent {

    public Player player;
    public Background background;

    public BackgroundComponent(Player player) {
        this.player = player;
    }

    public void setBackground(String name) {
        try {
            background = (Background) Class.forName("backgrounds." + name.replaceAll(" ", "_")).newInstance();
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
