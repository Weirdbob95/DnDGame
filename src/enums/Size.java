package enums;

public enum Size {

    TINY(1), SMALL(1), MEDIUM(2), LARGE(2), HUGE(3), GARGANTUAN(4);

    public final int squares;

    private Size(int squares) {
        this.squares = squares;
    }

    public static Size parse(String s) {
        switch (s) {
            case "T":
                return TINY;
            case "S":
                return SMALL;
            case "M":
                return MEDIUM;
            case "L":
                return LARGE;
            case "H":
                return HUGE;
            case "G":
                return GARGANTUAN;
        }
        return null;
    }
}
