package creature;

import core.AbstractComponent;
import java.util.ArrayList;
import queries.Query;
import queries.SelectQuery;
import util.Selectable;

public class LanguageComponent extends AbstractComponent {

    public Creature creature;
    public ArrayList<String> languages = new ArrayList();

    public LanguageComponent(Creature creature) {
        this.creature = creature;
    }

    public void chooseLanguage() {
        ArrayList<Selectable> all = Selectable.load("languages.txt");
        ArrayList<Selectable> choices = new ArrayList();
        for (Selectable s : all) {
            if (!languages.contains(s.getName())) {
                choices.add(s);
            }
        }
        languages.add(Query.ask(creature, new SelectQuery("Choose a language", choices)).response.getName());
    }
}
