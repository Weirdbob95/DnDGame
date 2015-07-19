package queries;

import creature.Creature;
import creature.CreatureComponent;

public abstract class Query {

    public static <Q extends Query> Q ask(Creature creature, Q query) {
        creature.getComponent(CreatureComponent.class).controller.handleQuery(query);
        return query;
    }
}
