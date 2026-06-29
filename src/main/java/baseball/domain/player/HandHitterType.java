package baseball.domain.player;

public enum HandHitterType {
    LEFT("좌타"),
    RIGHT("우타"),
    SWITCH("스위치히터");

    private final String description;

    HandHitterType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static HandHitterType findType(String input) {
        for (HandHitterType type : HandHitterType.values()) {
            if (type.description.equals(input)) {
                return type;
            }
        }
        return null;
    }
}