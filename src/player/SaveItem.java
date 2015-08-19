package player;

import java.util.HashMap;

public interface SaveItem {

    public void fromText(HashMap<String, String> text);

    public HashMap<String, String> toText();
}
