package player;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import util.Log;

public abstract class SaveUtils {

    public static HashMap<String, HashMap<String, String>> loadFile(String name) {
        HashMap<String, HashMap<String, String>> r = new HashMap();
        try {
            List<String> lines = Files.readAllLines(Paths.get(name));
            int pos = 0;
            while (pos < lines.size()) {
                HashMap<String, String> section = new HashMap();
                r.put(lines.get(pos).substring(1), section);
                pos++;
                while (!lines.get(pos).startsWith("/>")) {
                    String[] line = lines.get(pos).split("=");
                    section.put(line[0].trim(), line[1]);
                    pos++;
                }
                pos++;
            }
            return r;
        } catch (Exception ex) {
            Log.error(ex);
        }
        return null;
    }

    public static void saveFile(String name, Collection<SaveItem> contents) {
        String text = "";
        for (SaveItem i : contents) {
            text += "<" + i.getClass().getName() + "\n";
            HashMap<String, String> map = i.toText();
            for (String var : map.keySet()) {
                text += "    " + var + "=" + map.get(var) + "\n";
            }
            text += "/>\n";
        }
        PrintWriter writer;
        try {
            writer = new PrintWriter(name, "UTF-8");
            writer.print(text);
            writer.close();
        } catch (Exception ex) {
            Log.error(ex);
        }
    }
}
