package player;

import creature.Creature;
import grid.Square;
import java.util.ArrayList;
import java.util.HashMap;
import util.Log;

public class Player extends Creature {

    public ClassComponent clc;
    public RaceComponent rac;

    public Player(Square square) {
        super(square);

        clc = add(new ClassComponent(this));
        rac = add(new RaceComponent());

        add(new FightingStylesComponent(this));
        wc.setHands(2);
    }

    public static Player loadPlayer(String name, Square square) {
        Player p = new Player(square);
        HashMap<String, HashMap<String, String>> map = SaveUtils.loadFile(name);

        for (String s : map.keySet()) {
            try {
                SaveItem i = (SaveItem) Class.forName(s).getConstructor(Creature.class).newInstance(p);
                i.fromText(map.get(s));
            } catch (Exception ex) {
                Log.error(ex);
            }
        }

        return p;
    }

    public void save(String name) {
        ArrayList<SaveItem> toSave = new ArrayList();

        toSave.addAll(clc.classes);
        toSave.add(rac.race);

        SaveUtils.saveFile(name, toSave);
    }
}
