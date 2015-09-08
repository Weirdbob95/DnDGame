package creature;

import core.AbstractComponent;
import java.util.ArrayList;
import java.util.HashSet;
import queries.Query;
import queries.SelectQuery;
import util.Selectable;

public class LanguageComponent extends AbstractComponent {

    public Creature creature;
    public HashSet<String> languages = new HashSet();

    public LanguageComponent(Creature creature) {
        this.creature = creature;
    }

    public void chooseLanguage() {
        ArrayList<Selectable> choices = Selectable.load("languages.txt");
        choices.removeIf(s -> languages.stream().anyMatch(s.getName()::equals));
        languages.add(Query.ask(creature, new SelectQuery("Choose a language", choices)).response.getName());
    }
}
