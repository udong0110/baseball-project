package baseball.domain.player;

public enum PitcherPosition {
    SP("선발투수", "SP"),
    RP("구원투수", "RP"),
    CP("마무리투수", "CP");

    private final String positionName;
    private final String abbreviation;

    PitcherPosition(String positionName, String abbreviation) {
        this.positionName = positionName;
        this.abbreviation = abbreviation;
    }

    public String getPositionName() {
        return positionName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public static PitcherPosition getPosition(String input) {
        for (PitcherPosition position : PitcherPosition.values()) {
            if (position.abbreviation.equalsIgnoreCase(input) || position.positionName.equals(input)) {
                return position;
            }
        }
        return null;
    }
}