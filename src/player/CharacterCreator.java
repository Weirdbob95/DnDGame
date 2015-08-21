package player;

import core.Core;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import queries.PointBuyQuery;
import queries.Query;
import queries.SelectQuery;
import races.Race;
import util.Log;
import util.Selectable;
import util.SelectableImpl;
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
                        List<String> raceList = Files.readAllLines(Paths.get("races.txt"));
                        ArrayList<Selectable> raceChoices = new ArrayList();
                        for (String s : raceList) {
                            raceChoices.add(new SelectableImpl(s.split(" / ")));
                        }
                        String chosenRace = Query.ask(p, new SelectQuery("Choose your character's race", raceChoices)).response.getName();
                        p.rac.race = (Race) Class.forName("races." + chosenRace).newInstance();
                        p.rac.race.addTo(p);

                        //Choose class
                        List<String> classList = Files.readAllLines(Paths.get("classes.txt"));
                        ArrayList<Selectable> classChoices = new ArrayList();
                        for (String s : classList) {
                            classChoices.add(new SelectableImpl(s.split(" / ")));
                        }
                        String chosenClass = Query.ask(p, new SelectQuery("Choose your character's class", classChoices)).response.getName();
                        p.clc.addLevel(chosenClass);

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
