package player;

import core.Core;
import java.io.IOException;
import queries.PointBuyQuery;
import queries.Query;
import queries.SelectQuery;
import util.Log;
import util.Selectable;
import util.SerializationUtils;

public abstract class CharacterCreator {

    public static void main(String[] args) throws IOException {
        try {
            Core.init();

            new Thread() {
                @Override
                public void run() {
                    try {
                        Player p = new Player(null);

                        //Choose race
                        String chosenRace = Query.ask(p, new SelectQuery("Choose your character's race", Selectable.load("races.txt"))).response.getName();
                        p.rac.setRace(chosenRace);

                        //Choose class
                        String chosenClass = Query.ask(p, new SelectQuery("Choose your character's class", Selectable.load("classes.txt"))).response.getName();
                        p.clc.addLevel(chosenClass);

                        //Choose alignment
                        p.cdc.alignment = Query.ask(p, new SelectQuery("Choose your character's alignment", Selectable.load("alignments.txt"))).response.getName();

                        //Choose background
                        String chosenBack = Query.ask(p, new SelectQuery("Choose your character's background", Selectable.load("backgrounds.txt"))).response.getName();
                        p.bc.setBackground(chosenBack);

                        //Point buy
                        p.asc.setAll(Query.ask(p, new PointBuyQuery(27, p.asc.getAll(), new int[]{20, 20, 20, 20, 20, 20})).response);

                        p.clc.classes.get(0).levelTo(20);

                        SerializationUtils.save("bob.ser", p);

                        System.exit(0);
                    } catch (Exception ex) {
                        Log.error(ex);
                        ex.printStackTrace();
                    }
                }
            }.start();

            Core.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            Core.destroy();
        }
        System.exit(0);
    }
}
