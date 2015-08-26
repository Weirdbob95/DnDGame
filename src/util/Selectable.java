package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public interface Selectable {

    public default boolean equiv(Selectable s) {
        return s.getName().equals(getName()) && s.getDescription().equals(getDescription());
    }

    public String getDescription();

    public String getName();

    public static ArrayList<Selectable> load(String name) {
        try {
            List<String> list = Files.readAllLines(Paths.get("dat/" + name));
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
