package baseball.domain.player;

public enum HitterPosition {
    LEFT("좌타"),
    RIGHT("우타"),
    SWITCH("스위치히터");

    private final String description;

    HitterPosition(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static HitterPosition findType(String input) {
        for (HitterPosition type : HitterPosition.values()) {
            if (type.description.equals(input) || type.name().equalsIgnoreCase(input)) {
                return type;
            }
        }
        return null;
    }
}