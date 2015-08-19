package enums;

public enum AbilityScore {

    STR("Strength"),
    DEX("Dexterity"),
    CON("Constitution"),
    INT("Intelligence"),
    WIS("Wisdom"),
    CHA("Charisma");

    private final String name;

    private AbilityScore(String name) {
        this.name = name;
    }

    public String longName() {
        return name;
    }

    public String shortName() {
        return name.substring(0, 3);
    }
}
