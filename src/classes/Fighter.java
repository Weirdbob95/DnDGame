package classes;

import creature.Creature;
import enums.FightingStyle;
import java.util.HashMap;
import queries.Query;
import queries.SelectQuery;

public class Fighter extends PlayerClass {

    public FightingStyle fightingStyle;

    public Fighter(Creature creature) {
        super(creature);
    }

    @Override
    public void levelUp(int newLevel) {
        switch (newLevel) {
            case 1:
                fightingStyle = Query.ask(creature, new SelectQuery<FightingStyle>("Choose a fighting style", FightingStyle.values())).response;
                break;
        }
    }

    @Override
    public void fromText(HashMap<String, String> text) {
        fightingStyle = FightingStyle.valueOf(text.get("fighting style"));
    }

    @Override
    public HashMap<String, String> toText() {
        HashMap<String, String> r = new HashMap();
        r.put("fighting style", fightingStyle.name());
        return r;
    }
}
