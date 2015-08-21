package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public interface Selectable {

    public String getDescription();

    public String getName();

    public static ArrayList<Selectable> load(String name) {
        try {
            List<String> list = Files.readAllLines(Paths.get(name));
            ArrayList<Selectable> r = new ArrayList();
            for (String s : list) {
                r.add(new SelectableImpl(s.split(" / ")));
            }
            return r;
        } catch (IOException ex) {
            Log.error(ex);
        }
        return null;
    }
}
