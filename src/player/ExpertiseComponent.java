package player;

import core.AbstractComponent;
import enums.Skill;
import events.AbilityCheckEvent;
import events.EventListener;
import java.util.ArrayList;
import java.util.HashSet;
import queries.Query;
import queries.SelectQuery;
import util.Printable;

public class ExpertiseComponent extends AbstractComponent implements Printable {

    public HashSet<Skill> expertises;
    public Player player;

    public ExpertiseComponent(Player player) {
        this.player = player;
        expertises = new HashSet();
        EventListener.createListener(player, AbilityCheckEvent.class, e -> {
            if (expertises.contains(e.skill)) {
                e.bonus.set("Expertise", player.pc.prof);
            }
        });
    }

    public void chooseExpertise() {
        ArrayList<Skill> skillList = new ArrayList(player.pc.skillProfs);
        skillList.removeAll(expertises);
        Skill skill = Query.ask(player, new SelectQuery<Skill>("Choose a skill", skillList)).response;
        expertises.add(skill);
    }

    @Override
    public String print() {
        return "Skill Expertises: " + expertises.stream().map(f -> f.getName()).reduce((a, b) -> a + ", " + b).get();
    }
}
