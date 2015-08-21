package player;

import core.AbstractComponent;
import races.Race;
import util.Log;

public class RaceComponent extends AbstractComponent {

    public Player player;
    public Race race;

    public RaceComponent(Player player) {
        this.player = player;
    }

    public void setRace(String name) {
        try {
            race = (Race) Class.forName("races." + name).newInstance();
            race.addTo(player);
        } catch (Exception ex) {
            Log.error(ex);
        }
    }
}
